package com.example.shms;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class AppointmentActivity extends AppCompatActivity {
    private static final String TAG = "AppointmentActivity";
    private AutoCompleteTextView edtBacSi;
    private EditText edtThoiGian, edtTieuSuBenh, edtMoTa;
    private Button btnXemThongTin, btnXacNhan;
    private DatabaseHelper dbHelper;
    private List<String> doctorNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datlichkham);

        try {
            // Khởi tạo các thành phần
            initializeComponents();
            // Load danh sách bác sĩ
            loadDoctorList();
            // Thiết lập AutoComplete
            setupAutoComplete();
            // Thiết lập sự kiện click
            setupClickListeners();

            Log.d(TAG, "Activity initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage());
            Toast.makeText(this, "Có lỗi xảy ra khi khởi tạo màn hình", Toast.LENGTH_SHORT).show();
            finish(); // Đóng activity nếu có lỗi nghiêm trọng
        }
    }

    private void initializeComponents() {
        try {
            // Khởi tạo DatabaseHelper
            dbHelper = new DatabaseHelper(this);
            doctorNames = new ArrayList<>();

            // Ánh xạ các view
            edtBacSi = findViewById(R.id.editTextText5);
            edtThoiGian = findViewById(R.id.editTextText6);
            edtTieuSuBenh = findViewById(R.id.editTextText7);
            edtMoTa = findViewById(R.id.editTextText8);
            btnXemThongTin = findViewById(R.id.button5);
            btnXacNhan = findViewById(R.id.button6);

            Log.d(TAG, "Components initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error initializing components: " + e.getMessage());
            throw e; // Ném lại exception để được xử lý ở onCreate
        }
    }

    private void loadDoctorList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DatabaseHelper.TABLE_DOCTOR,
                    new String[]{"tenBacSi"},
                    null, null, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    doctorNames.add(cursor.getString(0));
                }
            }
            Log.d(TAG, "Loaded " + doctorNames.size() + " doctors");
        } catch (Exception e) {
            Log.e(TAG, "Error loading doctor list: " + e.getMessage());
            doctorNames.clear(); // Đảm bảo list trống nếu có lỗi
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void setupAutoComplete() {
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    doctorNames
            );
            edtBacSi.setAdapter(adapter);
            edtBacSi.setThreshold(1);
            Log.d(TAG, "AutoComplete setup completed");
        } catch (Exception e) {
            Log.e(TAG, "Error setting up AutoComplete: " + e.getMessage());
        }
    }

    private void setupClickListeners() {
        // Xử lý sự kiện xem thông tin bác sĩ
        btnXemThongTin.setOnClickListener(v -> {
            try {
                String doctorName = edtBacSi.getText().toString().trim();
                showDoctorInfo(doctorName);
            } catch (Exception e) {
                Log.e(TAG, "Error in btnXemThongTin click: " + e.getMessage());
                Toast.makeText(this, "Không thể hiển thị thông tin bác sĩ", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện xác nhận đặt lịch
        btnXacNhan.setOnClickListener(v -> {
            try {
                saveAppointment();
            } catch (Exception e) {
                Log.e(TAG, "Error in btnXacNhan click: " + e.getMessage());
                Toast.makeText(this, "Không thể đặt lịch", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDoctorInfo(String doctorName) {
        if (doctorName.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn bác sĩ", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DatabaseHelper.TABLE_DOCTOR,
                    null,
                    "tenBacSi = ?",
                    new String[]{doctorName},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String chuyenKhoa = cursor.getString(cursor.getColumnIndex("chuyenKhoa"));
                String kinhNghiem = cursor.getString(cursor.getColumnIndex("kinhNghiem"));
                String thongTinChiTiet = cursor.getString(cursor.getColumnIndex("thongTinChiTiet"));

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông tin bác sĩ")
                        .setMessage("Tên: " + doctorName + "\n\n" +
                                "Chuyên khoa: " + chuyenKhoa + "\n\n" +
                                "Kinh nghiệm: " + kinhNghiem + "\n\n" +
                                "Thông tin chi tiết: " + thongTinChiTiet)
                        .setPositiveButton("Đóng", null)
                        .show();

                Log.d(TAG, "Displayed info for doctor: " + doctorName);
            } else {
                Toast.makeText(this, "Không tìm thấy thông tin bác sĩ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error showing doctor info: " + e.getMessage());
            Toast.makeText(this, "Lỗi khi hiển thị thông tin bác sĩ", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void saveAppointment() {
        String bacSi = edtBacSi.getText().toString().trim();
        String thoiGian = edtThoiGian.getText().toString().trim();
        String tieuSuBenh = edtTieuSuBenh.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();

        if (bacSi.isEmpty() || thoiGian.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            // Lấy ID của bác sĩ
            int doctorId = getDoctorId(bacSi);
            if (doctorId == -1) {
                Toast.makeText(this, "Không tìm thấy thông tin bác sĩ", Toast.LENGTH_SHORT).show();
                return;
            }

            values.put("doctorId", doctorId);
            values.put("thoiGian", thoiGian);
            values.put("tieuSuBenh", tieuSuBenh);
            values.put("moTa", moTa);
            values.put("trangThai", "Đã đặt lịch");

            long result = db.insert(DatabaseHelper.TABLE_APPOINTMENT, null, values);
            if (result != -1) {
                Toast.makeText(this, "Đặt lịch thành công", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Appointment saved successfully");
                finish();
            } else {
                Toast.makeText(this, "Lỗi khi đặt lịch", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error saving appointment");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in saveAppointment: " + e.getMessage());
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private int getDoctorId(String doctorName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DatabaseHelper.TABLE_DOCTOR,
                    new String[]{"id"},
                    "tenBacSi = ?",
                    new String[]{doctorName},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting doctor ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}

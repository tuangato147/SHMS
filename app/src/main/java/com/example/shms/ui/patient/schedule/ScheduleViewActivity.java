package com.example.shms.ui.patient.schedule;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shms.R;
import com.example.shms.data.local.database.DatabaseHelper;

public class ScheduleViewActivity extends AppCompatActivity {
    private EditText edtBacSi, edtThoiGian, edtTieuSuBenh, edtMoTa, edtThongTinBS;
    private Button btnXacNhan;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemlichkham);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Ánh xạ các view
        edtBacSi = findViewById(R.id.editTextText5);
        edtThoiGian = findViewById(R.id.editTextText6);
        edtTieuSuBenh = findViewById(R.id.editTextText7);
        edtMoTa = findViewById(R.id.editTextText8);
        edtThongTinBS = findViewById(R.id.ttbs);
        btnXacNhan = findViewById(R.id.button8);

        // Load thông tin lịch khám
        loadAppointmentInfo();

        // Vô hiệu hóa các trường nhập liệu
        setEditableState(false);

        // Xử lý sự kiện nút Xác nhận (Quay lại)
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setEditableState(boolean editable) {
        edtBacSi.setEnabled(editable);
        edtThoiGian.setEnabled(editable);
        edtTieuSuBenh.setEnabled(editable);
        edtMoTa.setEnabled(editable);
        edtThongTinBS.setEnabled(editable);
    }

    private void loadAppointmentInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor appointmentCursor = null;
        try {
            // Lấy lịch khám mới nhất
            appointmentCursor = db.rawQuery(
                    "SELECT a.*, d.tenBacSi, d.chuyenKhoa, d.kinhNghiem, d.thongTinChiTiet " +
                            "FROM " + DatabaseHelper.TABLE_APPOINTMENT + " a " +
                            "JOIN " + DatabaseHelper.TABLE_DOCTOR + " d ON a.doctorId = d.id " +
                            "ORDER BY a.id DESC LIMIT 1", null);

            if (appointmentCursor != null && appointmentCursor.moveToFirst()) {
                // Hiển thị thông tin lịch khám
                String tenBacSi = appointmentCursor.getString(appointmentCursor.getColumnIndex("tenBacSi"));
                edtBacSi.setText(tenBacSi);
                edtThoiGian.setText(appointmentCursor.getString(appointmentCursor.getColumnIndex("thoiGian")));
                edtTieuSuBenh.setText(appointmentCursor.getString(appointmentCursor.getColumnIndex("tieuSuBenh")));
                edtMoTa.setText(appointmentCursor.getString(appointmentCursor.getColumnIndex("moTa")));

                // Hiển thị thông tin bác sĩ
                String chuyenKhoa = appointmentCursor.getString(appointmentCursor.getColumnIndex("chuyenKhoa"));
                String kinhNghiem = appointmentCursor.getString(appointmentCursor.getColumnIndex("kinhNghiem"));
                String thongTinChiTiet = appointmentCursor.getString(appointmentCursor.getColumnIndex("thongTinChiTiet"));

                String thongTinBS = "THÔNG TIN BÁC SĨ:\n\n" +
                        "Tên bác sĩ: " + tenBacSi + "\n\n" +
                        "Chuyên khoa: " + chuyenKhoa + "\n\n" +
                        "Kinh nghiệm: " + kinhNghiem + "\n\n" +
                        "Thông tin chi tiết: " + thongTinChiTiet;

                edtThongTinBS.setText(thongTinBS);

                Log.d("Schedule", "Appointment info loaded successfully");
            }
        } catch (Exception e) {
            Log.e("Schedule", "Error loading appointment info: " + e.getMessage());
        } finally {
            if (appointmentCursor != null) {
                appointmentCursor.close();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}

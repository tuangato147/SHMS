package com.example.shms.ui.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shms.ui.patient.appointment.AppointmentActivity;
import com.example.shms.data.local.database.DatabaseHelper;
import com.example.shms.data.local.entities.Doctor;
import com.example.shms.ui.adapters.DoctorAdapter;
import com.example.shms.ui.profile.ProfileActivity;
import com.example.shms.R;
import com.example.shms.ScheduleViewActivity;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {
    private static final String TAG = "MainMenuActivity";
    private AutoCompleteTextView searchEditText;
    private ImageView searchButton;
    private ImageButton btnProfile, btnAppointment, btnSchedule;
    private ImageView menuButton;
    private DatabaseHelper dbHelper;
    private String[] searchSuggestions = {"thông tin cá nhân", "đặt lịch", "lịch khám"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            initializeViews();
            setupAutoComplete();
            setupClickListeners();
            dbHelper = new DatabaseHelper(this);
            Log.d(TAG, "Activity initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage());
            Toast.makeText(this, "Có lỗi xảy ra khi khởi tạo ứng dụng", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews() {
        searchEditText = findViewById(R.id.editTextText4);
        searchButton = findViewById(R.id.imageView6);
        btnProfile = findViewById(R.id.imageButton);
        btnAppointment = findViewById(R.id.imageButton2);
        btnSchedule = findViewById(R.id.imageButton3);
        menuButton = findViewById(R.id.imageView7);
    }

    private void setupClickListeners() {
        searchButton.setOnClickListener(v -> performSearch());

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(MainMenuActivity.this, ProfileActivity.class)));

        btnAppointment.setOnClickListener(v ->
                startActivity(new Intent(MainMenuActivity.this, AppointmentActivity.class)));

        btnSchedule.setOnClickListener(v ->
                startActivity(new Intent(MainMenuActivity.this, ScheduleViewActivity.class)));

        menuButton.setOnClickListener(v -> showPopupMenu());
    }

    private void setupAutoComplete() {
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    searchSuggestions
            );
            searchEditText.setAdapter(adapter);
            searchEditText.setThreshold(1);

            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {}
            });
            Log.d(TAG, "AutoComplete setup completed");
        } catch (Exception e) {
            Log.e(TAG, "Error in setupAutoComplete: " + e.getMessage());
        }
    }

    private void showPopupMenu() {
        try {
            View popupView = getLayoutInflater().inflate(R.layout.popup_menu, null);
            final PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
            );

            TextView tvDoctorList = popupView.findViewById(R.id.tvDoctorList);
            tvDoctorList.setOnClickListener(v -> {
                showDoctorListDialog();
                popupWindow.dismiss();
            });

            popupWindow.showAsDropDown(menuButton);
            Log.d(TAG, "Popup menu shown");
        } catch (Exception e) {
            Log.e(TAG, "Error showing popup menu: " + e.getMessage());
            Toast.makeText(this, "Không thể hiển thị menu", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDoctorListDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.dialog_doctor_list, null);
            RecyclerView rvDoctors = view.findViewById(R.id.rvDoctors);

            rvDoctors.setLayoutManager(new LinearLayoutManager(this));
            List<Doctor> doctors = loadDoctors();

            DoctorAdapter adapter = new DoctorAdapter(doctors, this::showDoctorDetails);
            rvDoctors.setAdapter(adapter);

            builder.setView(view)
                    .setTitle("Danh sách bác sĩ")
                    .setPositiveButton("Đóng", null);

            AlertDialog dialog = builder.create();
            dialog.show();
            Log.d(TAG, "Doctor list dialog shown with " + doctors.size() + " doctors");
        } catch (Exception e) {
            Log.e(TAG, "Error showing doctor list: " + e.getMessage());
            Toast.makeText(this, "Không thể hiển thị danh sách bác sĩ", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Doctor> loadDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.query(DatabaseHelper.TABLE_DOCTOR,
                    new String[]{"id", "tenBacSi", "chuyenKhoa", "kinhNghiem", "thongTinChiTiet"},
                    null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndexOrThrow("id");
                int tenBacSiIndex = cursor.getColumnIndexOrThrow("tenBacSi");
                int chuyenKhoaIndex = cursor.getColumnIndexOrThrow("chuyenKhoa");
                int kinhNghiemIndex = cursor.getColumnIndexOrThrow("kinhNghiem");
                int thongTinChiTietIndex = cursor.getColumnIndexOrThrow("thongTinChiTiet");

                do {
                    try {
                        Doctor doctor = new Doctor(
                                cursor.getInt(idIndex),
                                cursor.getString(tenBacSiIndex),
                                cursor.getString(chuyenKhoaIndex),
                                cursor.getString(kinhNghiemIndex),
                                cursor.getString(thongTinChiTietIndex)
                        );
                        doctors.add(doctor);
                    } catch (Exception e) {
                        Log.e(TAG, "Error creating doctor object: " + e.getMessage());
                    }
                } while (cursor.moveToNext());
            }
            Log.d(TAG, "Loaded " + doctors.size() + " doctors from database");
        } catch (Exception e) {
            Log.e(TAG, "Error loading doctors: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return doctors;
    }

    private void showDoctorDetails(Doctor doctor) {
        try {
            new AlertDialog.Builder(this)
                    .setTitle("Thông tin chi tiết bác sĩ")
                    .setMessage(String.format(
                            "Tên: %s\n\nChuyên khoa: %s\n\nKinh nghiệm: %s\n\nThông tin chi tiết: %s",
                            doctor.getTenBacSi(),
                            doctor.getChuyenKhoa(),
                            doctor.getKinhNghiem(),
                            doctor.getThongTinChiTiet()
                    ))
                    .setPositiveButton("Đóng", null)
                    .show();
            Log.d(TAG, "Showing details for doctor: " + doctor.getTenBacSi());
        } catch (Exception e) {
            Log.e(TAG, "Error showing doctor details: " + e.getMessage());
            Toast.makeText(this, "Không thể hiển thị thông tin bác sĩ", Toast.LENGTH_SHORT).show();
        }
    }

    private void performSearch() {
        try {
            String searchText = searchEditText.getText().toString().trim().toLowerCase();
            if (!searchText.isEmpty()) {
                Intent intent = null;
                switch (searchText) {
                    case "thông tin cá nhân":
                        intent = new Intent(this, ProfileActivity.class);
                        break;
                    case "đặt lịch":
                        intent = new Intent(this, AppointmentActivity.class);
                        break;
                    case "lịch khám":
                        intent = new Intent(this, ScheduleViewActivity.class);
                        break;
                    default:
                        Toast.makeText(this, "Không tìm thấy chức năng", Toast.LENGTH_SHORT).show();
                        return;
                }
                if (intent != null) {
                    startActivity(intent);
                    Log.d(TAG, "Navigating to: " + searchText);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in performSearch: " + e.getMessage());
            Toast.makeText(this, "Có lỗi xảy ra khi tìm kiếm", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
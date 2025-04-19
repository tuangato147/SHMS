package com.example.shms.ui.profile;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shms.data.local.database.DatabaseHelper;
import com.example.shms.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {
    private EditText edtHoTen, edtNamSinh, edtNoiO, edtGioiTinh;
    private Button btnEdit, btnConfirm;
    private DatabaseHelper dbHelper;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtincanhan);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Ánh xạ các view
        edtHoTen = findViewById(R.id.editTextHoTen);
        edtNamSinh = findViewById(R.id.editTextNamSinh);
        edtNoiO = findViewById(R.id.editTextNoiO);
        edtGioiTinh = findViewById(R.id.editTextGioiTinh);
        btnEdit = findViewById(R.id.buttonSuaThongTin);
        btnConfirm = findViewById(R.id.buttonXacNhan);

        // Load thông tin người dùng
        loadUserInfo();

        // Vô hiệu hóa EditText ban đầu
        setEditableState(false);

        // Xử lý sự kiện nút Sửa thông tin
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditing = !isEditing;
                setEditableState(isEditing);
                btnEdit.setText(isEditing ? "Hủy" : "Sửa thông tin");
            }
        });

        // Xử lý sự kiện nút Xác nhận
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    saveUserInfo();
                }
            }
        });
    }

    private void setEditableState(boolean editable) {
        edtHoTen.setEnabled(editable);
        edtNamSinh.setEnabled(editable);
        edtNoiO.setEnabled(editable);
        edtGioiTinh.setEnabled(editable);
        btnConfirm.setEnabled(editable);
    }

    private void loadUserInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DatabaseHelper.TABLE_USER_INFO, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                edtHoTen.setText(cursor.getString(cursor.getColumnIndex("hoTen")));
                edtNamSinh.setText(cursor.getString(cursor.getColumnIndex("namSinh")));
                edtNoiO.setText(cursor.getString(cursor.getColumnIndex("noiO")));
                edtGioiTinh.setText(cursor.getString(cursor.getColumnIndex("gioiTinh")));
                Log.d("Profile", "User info loaded successfully");
            }
        } catch (Exception e) {
            Log.e("Profile", "Error loading user info: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void saveUserInfo() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", edtHoTen.getText().toString().trim());
        values.put("namSinh", edtNamSinh.getText().toString().trim());
        values.put("noiO", edtNoiO.getText().toString().trim());
        values.put("gioiTinh", edtGioiTinh.getText().toString().trim());
        values.put("lastUpdated", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date()));

        try {
            long result = db.insert(DatabaseHelper.TABLE_USER_INFO, null, values);
            if (result != -1) {
                Toast.makeText(this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                Log.d("Profile", "User info saved successfully");
                isEditing = false;
                setEditableState(false);
                btnEdit.setText("Sửa thông tin");
            } else {
                Toast.makeText(this, "Lỗi khi lưu thông tin", Toast.LENGTH_SHORT).show();
                Log.e("Profile", "Error saving user info");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Profile", "Error saving user info: " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}

package com.example.shms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HospitalDB";
    private static final int DATABASE_VERSION = 1;

    // Tên các bảng
    public static final String TABLE_USER_INFO = "UserInfo";
    public static final String TABLE_DOCTOR = "Doctor";
    public static final String TABLE_APPOINTMENT = "Appointment";

    // Tạo bảng UserInfo
    private static final String CREATE_USER_INFO_TABLE = "CREATE TABLE " + TABLE_USER_INFO + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "hoTen TEXT, " +
            "namSinh TEXT, " +
            "noiO TEXT, " +
            "gioiTinh TEXT, " +
            "lastUpdated DATETIME" +
            ")";

    // Tạo bảng Doctor
    private static final String CREATE_DOCTOR_TABLE = "CREATE TABLE " + TABLE_DOCTOR + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tenBacSi TEXT, " +
            "chuyenKhoa TEXT, " +
            "kinhNghiem TEXT, " +
            "thongTinChiTiet TEXT" +
            ")";

    // Tạo bảng Appointment
    private static final String CREATE_APPOINTMENT_TABLE = "CREATE TABLE " + TABLE_APPOINTMENT + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "doctorId INTEGER, " +
            "userId INTEGER, " +
            "thoiGian DATETIME, " +
            "tieuSuBenh TEXT, " +
            "moTa TEXT, " +
            "trangThai TEXT, " +
            "FOREIGN KEY (doctorId) REFERENCES " + TABLE_DOCTOR + "(id), " +
            "FOREIGN KEY (userId) REFERENCES " + TABLE_USER_INFO + "(id)" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_USER_INFO_TABLE);
            db.execSQL(CREATE_DOCTOR_TABLE);
            db.execSQL(CREATE_APPOINTMENT_TABLE);

            // Thêm dữ liệu mẫu cho bảng Doctor
            insertSampleDoctors(db);

            Log.d("Database", "Created tables successfully");
        } catch (Exception e) {
            Log.e("Database", "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop các bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);

        // Tạo lại các bảng
        onCreate(db);
        Log.d("Database", "Database upgraded from version " + oldVersion + " to " + newVersion);
    }

    private void insertSampleDoctors(SQLiteDatabase db) {
        // Thêm một số bác sĩ mẫu
        String[] sampleDoctors = {
                "INSERT INTO " + TABLE_DOCTOR + " (tenBacSi, chuyenKhoa, kinhNghiem, thongTinChiTiet) " +
                        "VALUES ('BS. Nguyễn Văn tâm', 'Tim mạch', '15 năm', 'Chuyên gia về bệnh tim mạch, tốt nghiệp ĐH Y Hà Nội')",

                "INSERT INTO " + TABLE_DOCTOR + " (tenBacSi, chuyenKhoa, kinhNghiem, thongTinChiTiet) " +
                        "VALUES ('BS. Trần Thị thúy', 'Nhi khoa', '10 năm', 'Chuyên khoa nhi, tốt nghiệp ĐH Y Dược TP.HCM')",

                "INSERT INTO " + TABLE_DOCTOR + " (tenBacSi, chuyenKhoa, kinhNghiem, thongTinChiTiet) " +
                        "VALUES ('BS. Lê Văn tuấn', 'Da liễu', '12 năm', 'Chuyên gia về bệnh da liễu, đào tạo tại Pháp')"
        };

        try {
            for (String query : sampleDoctors) {
                db.execSQL(query);
            }
            Log.d("Database", "Inserted sample doctors successfully");
        } catch (Exception e) {
            Log.e("Database", "Error inserting sample doctors: " + e.getMessage());
        }
    }
}

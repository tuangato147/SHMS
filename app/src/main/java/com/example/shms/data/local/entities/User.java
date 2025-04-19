// File: data/local/entities/User.java

package com.example.shms.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

/**
 * Entity class đại diện cho bảng User trong database
 * Sử dụng annotation của Room để định nghĩa cấu trúc bảng
 */
@Entity(tableName = "users")
public class User {
    // ID của user, tự động tăng
    @PrimaryKey(autoGenerate = true)
    private int id;

    // Tên đăng nhập của user
    @ColumnInfo(name = "username")
    private String username;

    // Mật khẩu của user (nên được mã hóa trước khi lưu)
    @ColumnInfo(name = "password")
    private String password;

    // Loại user: PATIENT, DOCTOR, STAFF
    @ColumnInfo(name = "user_type")
    private String userType;

    // Các thông tin cá nhân
    @ColumnInfo(name = "full_name")
    private String fullName;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "phone")
    private String phone;

    // Constructor
    public User(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ... các getter setter khác
}
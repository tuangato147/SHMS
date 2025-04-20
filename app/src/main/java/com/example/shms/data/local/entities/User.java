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
    @PrimaryKey
    private int id;
    private String username;
    private String password;
    // getters and setters
}
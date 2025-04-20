// File: data/local/entities/User.java

package com.example.shms.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import com.example.shms.utils.Constants;

/**
 * Entity class đại diện cho bảng User trong database
 * Sử dụng annotation của Room để định nghĩa cấu trúc bảng
 */
@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "role")
    private String role;

    @ColumnInfo(name = "full_name")
    private String fullName;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "phone")
    private String phone;

    // Thêm trường để đánh dấu multiple roles
    @ColumnInfo(name = "is_doctor")
    private boolean isDoctor = false;

    @ColumnInfo(name = "is_staff")
    private boolean isStaff = false;

    @ColumnInfo(name = "is_patient")
    private boolean isPatient = false;

    // Constructor mặc định (cần thiết cho Room)
    public User() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public boolean isPatient() {
        return isPatient;
    }

    public void setPatient(boolean patient) {
        isPatient = patient;
    }

    public boolean hasRole(String roleToCheck) {
        switch (roleToCheck) {
            case Constants.ROLE_ADMIN:
                return role.equals(Constants.ROLE_ADMIN);
            case Constants.ROLE_DOCTOR:
                return isDoctor;
            case Constants.ROLE_STAFF:
                return isStaff;
            case Constants.ROLE_PATIENT:
                return isPatient;
            default:
                return false;
        }
    }
}
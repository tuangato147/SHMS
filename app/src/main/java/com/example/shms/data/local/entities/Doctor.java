package com.example.shms.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "doctors")
public class Doctor {
    @PrimaryKey
    private int id;
    private String tenBacSi;
    private String chuyenKhoa;
    private String kinhNghiem;
    private String thongTinChiTiet;
    private int sodienthoai;
    private String email;

    public Doctor(int id, String tenBacSi, String chuyenKhoa, String kinhNghiem, String thongTinChiTiet, int sodienthoai, String email) {
        this.id = id;
        this.tenBacSi = tenBacSi;
        this.chuyenKhoa = chuyenKhoa;
        this.kinhNghiem = kinhNghiem;
        this.thongTinChiTiet = thongTinChiTiet;
        this.sodienthoai = sodienthoai;
        this.email = email;
    }
    // Thêm constructor mặc định
    public Doctor() {
    }


    public int getId() {
        return id;
    }

    public String getTenBacSi() {
        return tenBacSi;
    }

    public String getChuyenKhoa() {
        return chuyenKhoa;
    }

    public String getKinhNghiem() {
        return kinhNghiem;
    }

    public String getThongTinChiTiet() {
        return thongTinChiTiet;
    }

    public int getSodienthoai() {
        return sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    // created setName
    public void setName(String name) {
        this.tenBacSi = name;
    }
    // created setSpecialty
    public void setSpecialty(String specialty) {
        this.chuyenKhoa = specialty;
    }
    // created setPhone
    public void setPhone(int phone) {
        this.sodienthoai = phone;
    }
    //created setEmail
    public void setEmail(String email) {
        this.email = email;
    }

}

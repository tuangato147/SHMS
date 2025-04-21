package com.example.shms.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

@Entity(tableName = "doctors")
public class Doctor {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "ten_bac_si")
    private String tenBacSi;

    @ColumnInfo(name = "chuyen_khoa")
    private String chuyenKhoa;

    @ColumnInfo(name = "kinh_nghiem")
    private String kinhNghiem;

    @ColumnInfo(name = "thong_tin_chi_tiet")
    private String thongTinChiTiet;

    @ColumnInfo(name = "so_dien_thoai")
    private int sodienthoai;

    @ColumnInfo(name = "email")
    private String email;

    // Constructor mặc định cho Room
    public Doctor() {
    }

    // Constructor đầy đủ - đánh dấu @Ignore
    @Ignore
    public Doctor(int id, String tenBacSi, String chuyenKhoa, String kinhNghiem,
                  String thongTinChiTiet, int sodienthoai, String email) {
        this.id = id;
        this.tenBacSi = tenBacSi;
        this.chuyenKhoa = chuyenKhoa;
        this.kinhNghiem = kinhNghiem;
        this.thongTinChiTiet = thongTinChiTiet;
        this.sodienthoai = sodienthoai;
        this.email = email;
    }

    // Thêm constructor mới cho trường hợp không cần số điện thoại và email
    @Ignore
    public Doctor(int id, String tenBacSi, String chuyenKhoa, String kinhNghiem,
                  String thongTinChiTiet) {
        this.id = id;
        this.tenBacSi = tenBacSi;
        this.chuyenKhoa = chuyenKhoa;
        this.kinhNghiem = kinhNghiem;
        this.thongTinChiTiet = thongTinChiTiet;
        this.sodienthoai = 0; // giá trị mặc định
        this.email = ""; // giá trị mặc định
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenBacSi() {
        return tenBacSi;
    }

    public void setTenBacSi(String tenBacSi) {
        this.tenBacSi = tenBacSi;
    }

    public String getChuyenKhoa() {
        return chuyenKhoa;
    }

    public void setChuyenKhoa(String chuyenKhoa) {
        this.chuyenKhoa = chuyenKhoa;
    }

    public String getKinhNghiem() {
        return kinhNghiem;
    }

    public void setKinhNghiem(String kinhNghiem) {
        this.kinhNghiem = kinhNghiem;
    }

    public String getThongTinChiTiet() {
        return thongTinChiTiet;
    }

    public void setThongTinChiTiet(String thongTinChiTiet) {
        this.thongTinChiTiet = thongTinChiTiet;
    }

    public int getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(int sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
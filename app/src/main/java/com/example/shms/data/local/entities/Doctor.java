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

    public Doctor(int id, String tenBacSi, String chuyenKhoa, String kinhNghiem, String thongTinChiTiet) {
        this.id = id;
        this.tenBacSi = tenBacSi;
        this.chuyenKhoa = chuyenKhoa;
        this.kinhNghiem = kinhNghiem;
        this.thongTinChiTiet = thongTinChiTiet;
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
}

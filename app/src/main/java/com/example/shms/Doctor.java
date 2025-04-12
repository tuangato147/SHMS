package com.example.shms;

public class Doctor {
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

    // Getters
    public int getId() { return id; }
    public String getTenBacSi() { return tenBacSi; }
    public String getChuyenKhoa() { return chuyenKhoa; }
    public String getKinhNghiem() { return kinhNghiem; }
    public String getThongTinChiTiet() { return thongTinChiTiet; }
}

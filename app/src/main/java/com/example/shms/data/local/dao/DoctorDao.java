package com.example.shms.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shms.data.local.entities.Doctor;

import java.util.List;

@Dao
public interface DoctorDao {
    @Query("SELECT * FROM doctors")
    LiveData<List<Doctor>> getAllDoctors();

    @Query("SELECT * FROM doctors WHERE id = :id")
    LiveData<Doctor> getDoctorById(int id);

    // Sửa tên cột thành chuyen_khoa để khớp với @ColumnInfo trong Doctor entity
    @Query("SELECT * FROM doctors WHERE chuyen_khoa = :chuyenKhoa")
    LiveData<List<Doctor>> getDoctorsByChuyenKhoa(String chuyenKhoa);

    @Insert
    long insert(Doctor doctor);

    @Update
    void update(Doctor doctor);

    @Delete
    void delete(Doctor doctor);
    // Lấy danh sách bác sĩ theo chuyên khoa( tìm kiếm linh hoạt hơn (không phân biệt chữ hoa/thường và tìm kiếm một phần)
    @Query("SELECT * FROM doctors WHERE LOWER(chuyen_khoa) LIKE '%' || LOWER(:specialization) || '%'")
    LiveData<List<Doctor>> getDoctorsBySpecialization(String specialization);

    // Thêm các query hữu ích khác
    @Query("SELECT * FROM doctors WHERE ten_bac_si LIKE '%' || :searchQuery || '%'")
    LiveData<List<Doctor>> searchDoctors(String searchQuery);

    @Query("DELETE FROM doctors")
    void deleteAll();
}
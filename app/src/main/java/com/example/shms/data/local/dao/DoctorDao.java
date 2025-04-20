package com.example.shms.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shms.data.local.entities.Doctor;

import java.util.List;

@Dao
public interface DoctorDao {
    @Query("SELECT * FROM doctors")
    LiveData<List<Doctor>> getAllDoctors();

    @Query("SELECT * FROM doctors WHERE id = :id")
    LiveData<Doctor> getDoctorById(int id);

    @Insert
    void insert(Doctor doctor);
}

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

    @Query("SELECT * FROM doctors WHERE specialization = :specialization")
    LiveData<List<Doctor>> getDoctorsBySpecialization(String specialization);

    @Insert
    long insert(Doctor doctor);

    @Update
    void update(Doctor doctor);

    @Delete
    void delete(Doctor doctor);
}

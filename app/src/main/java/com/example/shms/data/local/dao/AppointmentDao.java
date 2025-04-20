package com.example.shms.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shms.data.local.entities.Appointment;

import java.sql.Date;
import java.util.List;

@Dao
public interface AppointmentDao {
    @Query("SELECT * FROM appointments WHERE patient_id = :patientId")
    LiveData<List<Appointment>> getAppointmentsByPatient(int patientId);

    @Query("SELECT * FROM appointments WHERE doctor_id = :doctorId")
    LiveData<List<Appointment>> getAppointmentsByDoctor(int doctorId);

    @Query("SELECT * FROM appointments WHERE appointment_date BETWEEN :startDate AND :endDate")
    LiveData<List<Appointment>> getAppointmentsBetweenDates(Date startDate, Date endDate);

    @Insert
    long insert(Appointment appointment);

    @Update
    void update(Appointment appointment);

    @Delete
    void delete(Appointment appointment);
}

package com.example.shms.data.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "appointments")
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "patient_id")
    private int patientId;

    @ColumnInfo(name = "doctor_id")
    private int doctorId;

    @ColumnInfo(name = "appointment_date")
    private Date appointmentDate;

    @ColumnInfo(name = "status")
    private String status;

    // Getters and Setters
}
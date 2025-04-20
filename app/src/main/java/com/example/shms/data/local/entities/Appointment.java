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

    @ColumnInfo(name = "date_time")
    private Date dateTime;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}

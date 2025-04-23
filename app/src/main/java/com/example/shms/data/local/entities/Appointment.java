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

//    @ColumnInfo(name = "status")
//    private String status;

    @ColumnInfo(name = "date_time")
    private Date dateTime;
    @ColumnInfo(name = "start_time")
    private String startTime;

    @ColumnInfo(name = "end_time")
    private String endTime;

    @ColumnInfo(name = "department")
    private String department; // Khoa nội, Khoa ngoại, Khoa thần kinh

    @ColumnInfo(name = "room_number")
    private String roomNumber;

    @ColumnInfo(name = "bed_number")
    private String bedNumber;

    @ColumnInfo(name = "medical_history")
    private String medicalHistory;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "status")
    private String status; // PENDING, APPROVED, CANCELLED, COMPLETED

    @ColumnInfo(name = "monitored")
    private boolean monitored;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "approved_at")
    private Date approvedAt;

    @ColumnInfo(name = "cancelled_at")
    private Date cancelledAt;

    @ColumnInfo(name = "completed_at")
    private Date completedAt;

    @ColumnInfo(name = "cancel_reason")
    private String cancelReason;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMonitored() {
        return monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Date approvedAt) {
        this.approvedAt = approvedAt;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}

package com.example.shms.data.local.database;

import android.content.Context;
import android.util.Log;

import com.example.shms.data.local.entities.Appointment;
import com.example.shms.data.local.entities.Doctor;
import com.example.shms.data.local.entities.Schedule;
import com.example.shms.data.local.entities.User;

public class DataIntegrityChecker {
    private AppDatabase db;

    public DataIntegrityChecker(Context context) {
        db = AppDatabase.getDatabase(context);
    }

    private void verifyDoctors() {
        db.doctorDao().getAllDoctors().observeForever(doctors -> {
            for (Doctor doctor : doctors) {
                if (!isValidDoctor(doctor)) {
                    Log.e("DataIntegrity", "Invalid doctor data found: " + doctor.getId());
                    // Xử lý dữ liệu không hợp lệ
                }
            }
        });
    }

    private boolean isValidDoctor(Doctor doctor) {
        return doctor.getName() != null &&
                doctor.getSpecialization() != null;
    }

    private void verifyAppointments() {
        db.appointmentDao().getAllAppointments().observeForever(appointments -> {
            for (Appointment appointment : appointments) {
                if (!isValidAppointment(appointment)) {
                    Log.e("DataIntegrity", "Invalid appointment data found: " + appointment.getId());
                    // Xử lý dữ liệu không hợp lệ
                }
            }
        });
    }

    private boolean isValidAppointment(Appointment appointment) {
        return appointment.getDoctorId() > 0 &&
                appointment.getPatientId() > 0 &&
                appointment.getDateTime() != null;
    }

    private void verifySchedules() {
        db.scheduleDao().getAllSchedules().observeForever(schedules -> {
            for (Schedule schedule : schedules) {
                if (!isValidSchedule(schedule)) {
                    Log.e("DataIntegrity", "Invalid schedule data found: " + schedule.getId());
                    // Xử lý dữ liệu không hợp lệ
                }
            }
        });
    }

    private boolean isValidSchedule(Schedule schedule) {
        return schedule.getDoctorId() > 0 &&
                schedule.getWorkingHours() != null;
    }

    private void verifyUsers() {
        db.userDao().getAllUsers().observeForever(users -> {
            for (User user : users) {
                if (!isValidUser(user)) {
                    Log.e("DataIntegrity", "Invalid user data found: " + user.getId());
                    // Handle invalid data
                }
            }
        });
    }

    private boolean isValidUser(User user) {
        return user.getUsername() != null &&
                user.getPassword() != null &&
                user.getRole() != null;
    }

    // Similar methods for other entities
}
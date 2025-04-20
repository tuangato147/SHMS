package com.example.shms.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shms.data.local.dao.AppointmentDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.Appointment;

import java.sql.Date;
import java.util.List;

public class AppointmentRepository {
    private AppointmentDao appointmentDao;
    private LiveData<List<Appointment>> allAppointments;

    public AppointmentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        appointmentDao = db.appointmentDao();
    }

    public LiveData<List<Appointment>> getAppointmentsByPatient(int patientId) {
        return appointmentDao.getAppointmentsByPatient(patientId);
    }

    public LiveData<List<Appointment>> getAppointmentsByDoctor(int doctorId) {
        return appointmentDao.getAppointmentsByDoctor(doctorId);
    }

    public LiveData<List<Appointment>> getAppointmentsBetweenDates(Date startDate, Date endDate) {
        return appointmentDao.getAppointmentsBetweenDates(startDate, endDate);
    }

    public void insert(Appointment appointment) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appointmentDao.insert(appointment);
        });
    }

    public void update(Appointment appointment) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            appointmentDao.update(appointment);
        });
    }
}

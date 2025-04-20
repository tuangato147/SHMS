package com.example.shms.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shms.data.local.dao.DoctorDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.Doctor;

import java.util.List;

public class DoctorRepository {
    private DoctorDao doctorDao;
    private LiveData<List<Doctor>> allDoctors;

    public DoctorRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        doctorDao = db.doctorDao();
        allDoctors = doctorDao.getAllDoctors();

    }

    // Get all doctors
    public LiveData<List<Doctor>> getAllDoctors() {
        return allDoctors;
    }

    // Get doctor by ID
    public LiveData<Doctor> getDoctorById(int id) {
        return doctorDao.getDoctorById(id);
    }

    // Get doctors by specialization
    public LiveData<List<Doctor>> getDoctorsBySpecialization(String specialization) {
        return doctorDao.getDoctorsBySpecialization(specialization);
    }

    // Insert new doctor
    public void insert(Doctor doctor) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            doctorDao.insert(doctor);
        });
    }

    // Update doctor info
    public void update(Doctor doctor) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            doctorDao.update(doctor);
        });
    }
    // Delete doctor
    public void delete(Doctor doctor) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            doctorDao.delete(doctor);
        });
    }
}

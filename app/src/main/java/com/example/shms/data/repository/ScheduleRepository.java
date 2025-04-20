package com.example.shms.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shms.data.local.dao.ScheduleDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.Schedule;

import java.util.List;

public class ScheduleRepository {
    private ScheduleDao scheduleDao;

    public ScheduleRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        scheduleDao = db.scheduleDao();
    }

    public LiveData<List<Schedule>> getDoctorSchedule(int doctorId) {
        return scheduleDao.getDoctorSchedule(doctorId);
    }

    public LiveData<List<Schedule>> getSchedulesByDay(String dayOfWeek) {
        return scheduleDao.getSchedulesByDay(dayOfWeek);
    }

    public void insert(Schedule schedule) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            scheduleDao.insert(schedule);
        });
    }

    public void update(Schedule schedule) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            scheduleDao.update(schedule);
        });
    }
}

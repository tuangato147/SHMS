package com.example.shms.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shms.data.local.entities.Schedule;

import java.util.List;

@Dao
public interface ScheduleDao {
    @Query("SELECT * FROM schedules WHERE doctor_id = :doctorId")
    LiveData<List<Schedule>> getDoctorSchedule(int doctorId);

    @Query("SELECT * FROM schedules WHERE day_of_week = :dayOfWeek")
    LiveData<List<Schedule>> getSchedulesByDay(String dayOfWeek);

    @Insert
    long insert(Schedule schedule);

    @Update
    void update(Schedule schedule);

    @Delete
    void delete(Schedule schedule);
}

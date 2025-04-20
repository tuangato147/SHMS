package com.example.shms.data.local.entities;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
@Entity(tableName = "schedules")
public class Schedule {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "doctor_id")
    private int doctorId;

    @ColumnInfo(name = "day_of_week")
    private String dayOfWeek;

    @ColumnInfo(name = "start_time")
    private String startTime;

    @ColumnInfo(name = "end_time")
    private String endTime;

    // Getters and Setters
}

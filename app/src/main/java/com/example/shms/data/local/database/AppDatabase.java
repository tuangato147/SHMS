// File: data/local/database/AppDatabase.java

package com.example.shms.data.local.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.shms.data.local.dao.DoctorDao;
import com.example.shms.data.local.dao.UserDao;
import com.example.shms.data.local.entities.Appointment;
import com.example.shms.data.local.entities.Doctor;
import com.example.shms.data.local.entities.Schedule;
import com.example.shms.data.local.entities.User;
// Import các entity khác khi cần

/**
 * Class chính để quản lý database của ứng dụng
 * Sử dụng Room Database với các entity đã định nghĩa
 */
@Database(entities = {User.class, Doctor.class, Appointment.class, Schedule.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract DoctorDao doctorDao();
    public abstract AppointmentDao appointmentDao();
    public abstract ScheduleDao scheduleDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "shms_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
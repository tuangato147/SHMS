// File: data/local/database/AppDatabase.java

package com.example.shms.data.local.database;

//import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shms.data.local.dao.AppointmentDao;
import com.example.shms.data.local.dao.DoctorDao;
import com.example.shms.data.local.dao.NotificationDao;
import com.example.shms.data.local.dao.ScheduleDao;
import com.example.shms.data.local.dao.UserDao;
import com.example.shms.data.local.entities.Appointment;
import com.example.shms.data.local.entities.Doctor;
import com.example.shms.data.local.entities.Schedule;
import com.example.shms.data.local.entities.User;
import com.example.shms.utils.Constants;
import com.example.shms.data.local.entities.Notification;  // Đảm bảo import này tồn tại

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// Import các entity khác khi cần
import com.example.shms.data.local.converter.DateConverter;  // Cập nhật import
//import com.example.shms.data.local.entities.Notification;

/**
 * Class chính để quản lý database của ứng dụng
 * Sử dụng Room Database với các entity đã định nghĩa
 */
@Database(
        entities = {
                User.class,
                Doctor.class,
                Appointment.class,
                Schedule.class,
                Notification.class
        },
        version = 1
)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public abstract UserDao userDao();
    public abstract DoctorDao doctorDao();
    public abstract AppointmentDao appointmentDao();
    public abstract ScheduleDao scheduleDao();
    public abstract NotificationDao notificationDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "shms_database")
                            .fallbackToDestructiveMigration()  // Thêm dòng này
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Tạo tài khoản admin mặc định
            databaseWriteExecutor.execute(() -> {
                UserDao userDao = INSTANCE.userDao();

                // Kiểm tra xem admin account đã tồn tại chưa
                LiveData<User> adminUser = userDao.getUserByUsername(Constants.DEFAULT_ADMIN_USERNAME);// viết hàm getUserByUsername trong UserDao

                if (adminUser.getValue() == null) {
                    User admin = new User();
                    admin.setUsername(Constants.DEFAULT_ADMIN_USERNAME);
                    admin.setPassword(Constants.DEFAULT_ADMIN_PASSWORD);
                    admin.setRole(Constants.ROLE_ADMIN);
                    userDao.insert(admin);
                }
            });
        }
    };
}

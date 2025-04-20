package com.example.shms.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.shms.data.local.AppDatabase;
import com.example.shms.data.local.dao.NotificationDao;
import com.example.shms.data.local.entities.NotificationEntity;
import java.util.List;

public class NotificationRepository {
    private NotificationDao notificationDao;
    private LiveData<List<NotificationEntity>> allNotifications;

    public NotificationRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        notificationDao = database.notificationDao();
    }

    public LiveData<List<NotificationEntity>> getNotificationsForUser(int userId) {
        return notificationDao.getNotificationsForUser(userId);
    }

    public LiveData<List<NotificationEntity>> getUnreadNotifications() {
        return notificationDao.getUnreadNotifications();
    }

    public void insert(NotificationEntity notification) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationDao.insert(notification);
        });
    }

    public void markAllAsRead(int userId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationDao.markAllAsRead(userId);
        });
    }
}

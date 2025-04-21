package com.example.shms.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.shms.data.local.dao.NotificationDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.Notification;
import java.util.List;

public class NotificationRepository {
    private NotificationDao notificationDao;
    private LiveData<List<Notification>> allNotifications; // Sửa notification thành Notification

    public NotificationRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        notificationDao = database.notificationDao();
        allNotifications = notificationDao.getAllNotifications(); // Thêm dòng này để khởi tạo allNotifications
    }

    // Lấy tất cả notifications
    public LiveData<List<Notification>> getAllNotifications() {
        return allNotifications;
    }

    // Lấy notifications chưa đọc
    public LiveData<List<Notification>> getUnreadNotifications() {
        return notificationDao.getUnreadNotifications();
    }

    // Thêm notification mới
    public void insert(Notification notification) { // Sửa NotificationEntity thành Notification
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationDao.insert(notification);
        });
    }

    // Cập nhật notification
    public void update(Notification notification) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationDao.update(notification);
        });
    }

    // Xóa notification
    public void delete(Notification notification) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationDao.delete(notification);
        });
    }

    // Xóa tất cả notifications
    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationDao.deleteAll();
        });
    }

    // Đánh dấu tất cả là đã đọc
    public void markAllAsRead(int userId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Thêm method này vào NotificationDao nếu cần
            notificationDao.markAllAsRead(userId);
        });
    }
}
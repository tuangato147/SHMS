package com.example.shms.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.shms.data.local.entities.NotificationEntity;
import java.util.List;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    List<NotificationEntity> getAllNotifications();

    @Query("SELECT * FROM notifications WHERE isRead = 0")
    List<NotificationEntity> getUnreadNotifications();

    @Insert
    void insert(NotificationEntity notification);

    @Update
    void update(NotificationEntity notification);

    @Delete
    void delete(NotificationEntity notification);

    @Query("DELETE FROM notifications")
    void deleteAll();
}

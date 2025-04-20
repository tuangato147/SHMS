package com.example.shms.data.local.dao;

import android.app.Notification;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM notifications WHERE user_id = :userId")
    LiveData<List<Notification>> getNotificationsForUser(int userId);

    @Query("SELECT * FROM notifications WHERE is_read = 0")
    LiveData<List<Notification>> getUnreadNotifications();

    @Insert
    long insert(Notification notification);

    @Update
    void update(Notification notification);

    @Delete
    void delete(Notification notification);
}

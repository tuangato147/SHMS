package com.example.shms.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.example.shms.data.local.entities.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Insert
    void insert(User user);
}



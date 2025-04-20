package com.example.shms.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shms.data.local.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    LiveData<User> login(String username, String password);

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<User> getUserById(int id);

    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE role = :role")
    LiveData<List<User>> getUsersByRole(String role);
}

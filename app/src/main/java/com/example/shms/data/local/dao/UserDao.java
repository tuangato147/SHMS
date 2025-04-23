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
    // Authentication queries
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    LiveData<User> login(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password AND (role = :role OR is_doctor = 1 OR is_staff = 1 OR is_patient = 1)")
    LiveData<User> loginWithRole(String username, String password, String role);

    // Thêm method để kiểm tra username sync
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUserByUsernameSync(String username);

    // User management queries
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<User> getUserById(int id);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE role = :role")
    LiveData<List<User>> getUsersByRole(String role);

    // CRUD operations
    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
package com.example.shms.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.shms.data.local.dao.UserDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
    }

    // Login methods
    public LiveData<User> login(String username, String password) {
        return userDao.login(username, password);
    }

    public LiveData<User> loginWithRole(String username, String password, String role) {
        return userDao.loginWithRole(username, password, role);
    }

    // CRUD operations
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }

    // Thêm các methods khác tương tự
}

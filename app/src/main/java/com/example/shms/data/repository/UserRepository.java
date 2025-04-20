package com.example.shms.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shms.data.local.dao.UserDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.User;

import java.util.List;

// data/repository/UserRepository.java
public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
    }

    public LiveData<User> login(String username, String password) {
        return userDao.login(username, password);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }

    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.update(user);
        });
    }

    public void delete(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(user);
        });
    }
}

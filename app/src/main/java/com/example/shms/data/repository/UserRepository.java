package com.example.shms.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shms.data.local.dao.UserDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.User;

// data/repository/UserRepository.java
public class UserRepository {
    private UserDao userDao;
    private LiveData<User> currentUser;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }
}

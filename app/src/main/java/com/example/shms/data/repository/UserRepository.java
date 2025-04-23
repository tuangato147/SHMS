package com.example.shms.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.shms.data.local.dao.UserDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.User;
import com.example.shms.utils.Constants;
import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
    }

    // Authentication methods
    public LiveData<User> login(String username, String password) {
        return userDao.login(username, password);
    }

    public LiveData<User> loginWithRole(String username, String password, String role) {
        return userDao.loginWithRole(username, password, role);
    }

    // Registration method với callback
    public void register(User user, OnRegistrationCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                // Kiểm tra username đã tồn tại
                User existingUser = userDao.getUserByUsernameSync(user.getUsername());
                if (existingUser != null) {
                    callback.onFailure("Username đã tồn tại");
                    return;
                }

                // Insert user mới
                long userId = userDao.insert(user);
                if (userId > 0) {
                    callback.onSuccess();
                } else {
                    callback.onFailure("Đăng ký thất bại");
                }
            } catch (Exception e) {
                callback.onFailure(e.getMessage());
            }
        });
    }

    // User management methods
    public LiveData<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<List<User>> getUsersByRole(String role) {
        return userDao.getUsersByRole(role);
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

    // Callback interface cho đăng ký
    public interface OnRegistrationCallback {
        void onSuccess();
        void onFailure(String error);
    }
}
package com.example.shms.data.local.database;

import android.content.Context;
import android.util.Log;

import com.example.shms.data.local.entities.User;

public class DataIntegrityChecker {
    private AppDatabase db;

    public DataIntegrityChecker(Context context) {
        db = AppDatabase.getDatabase(context);
    }

    public void verifyDataIntegrity() {
        verifyUsers();
        verifyDoctors();
        verifyAppointments();
        verifySchedules();
    }

    private void verifyUsers() {
        db.userDao().getAllUsers().observeForever(users -> {
            for (User user : users) {
                if (!isValidUser(user)) {
                    Log.e("DataIntegrity", "Invalid user data found: " + user.getId());
                    // Handle invalid data
                }
            }
        });
    }

    private boolean isValidUser(User user) {
        return user.getUsername() != null &&
                user.getPassword() != null &&
                user.getRole() != null;
    }

    // Similar methods for other entities
}
package com.example.shms.data.local.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shms.data.local.entities.User;

public class MigrationHelper {
    private SQLiteDatabase oldDb;
    private AppDatabase newDb;
    private Context context;

    public MigrationHelper(Context context, String oldDbPath) {
        this.context = context;
        this.oldDb = SQLiteDatabase.openDatabase(oldDbPath, null, SQLiteDatabase.OPEN_READONLY);
        this.newDb = AppDatabase.getDatabase(context);
    }

    public void migrateData() {
        migrateUsers();
        migrateDoctors();
        migrateAppointments();
        migrateSchedules();
    }

    private void migrateUsers() {
        Cursor cursor = oldDb.query("users", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            // ... set other fields

            AppDatabase.databaseWriteExecutor.execute(() -> {
                newDb.userDao().insert(user);
            });
        }
        cursor.close();
    }

    private void migrateDoctors() {
        // Similar implementation for doctors table
    }

    private void migrateAppointments() {
        // Similar implementation for appointments table
    }

    private void migrateSchedules() {
        // Similar implementation for schedules table
    }
}
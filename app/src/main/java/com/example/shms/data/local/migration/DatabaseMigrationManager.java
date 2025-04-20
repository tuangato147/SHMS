package com.example.shms.data.local.migration;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.database.DatabaseHelper;
import com.example.shms.data.local.entities.User;

public class DatabaseMigrationManager {
    private Context context;
    private SQLiteDatabase oldDb;
    private AppDatabase newDb;

    public DatabaseMigrationManager(Context context) {
        this.context = context;
        this.newDb = AppDatabase.getDatabase(context);
    }

    public void startMigration() {
        if (!isMigrationNeeded()) {
            return;
        }

        try {
            openOldDatabase();
            migrateData();
            markMigrationComplete();
        } catch (Exception e) {
            Log.e("Migration", "Error during migration", e);
        } finally {
            closeOldDatabase();
        }
    }

    private void openOldDatabase() {
        oldDb = new DatabaseHelper(context).getReadableDatabase();
    }

    private void migrateData() {
        migrateUsers();
        migrateDoctors();
        migrateAppointments();
        migrateSchedules();
    }

    private void migrateUsers() {
        Cursor cursor = oldDb.query("users", null, null, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            // Set other fields

            AppDatabase.databaseWriteExecutor.execute(() -> {
                newDb.userDao().insert(user);
            });
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private boolean isMigrationNeeded() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return !prefs.getBoolean("DB_MIGRATED", false);
    }

    private void markMigrationComplete() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("DB_MIGRATED", true).apply();
    }

    private void closeOldDatabase() {
        if (oldDb != null && oldDb.isOpen()) {
            oldDb.close();
        }
    }
}

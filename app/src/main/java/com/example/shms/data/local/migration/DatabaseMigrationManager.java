package com.example.shms.data.local.migration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.database.DatabaseHelper;
import com.example.shms.data.local.entities.User;
import com.example.shms.data.local.entities.Doctor;
import com.example.shms.data.local.entities.Notification;
import com.example.shms.data.local.entities.Appointment;
import com.example.shms.data.local.entities.Schedule;

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
        migrateNotifications();
    }

    @SuppressLint("Range")
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

    private void migrateDoctors() {
        Cursor cursor = oldDb.query("doctors", null, null, null, null, null, null);
        if (cursor != null) {
            try {
                // Get column indices
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                int specialtyIndex = cursor.getColumnIndex("specialty");
                int phoneIndex = cursor.getColumnIndex("phone");
                int emailIndex = cursor.getColumnIndex("email");

                // Verify all columns exist
                if (idIndex < 0 || nameIndex < 0 || specialtyIndex < 0 || 
                    phoneIndex < 0 || emailIndex < 0) {
                    Log.e("Migration", "One or more columns missing in doctors table");
                    return;
                }

                while (cursor.moveToNext()) {
                    try {
                        Doctor doctor = new Doctor();
                        doctor.setId(cursor.getInt(idIndex)); // created setId method in Doctor class
                        doctor.setName(cursor.getString(nameIndex)); // created setName method in Doctor class
                        doctor.setSpecialty(cursor.getString(specialtyIndex)); // created setSpecialty method in Doctor class
                        doctor.setPhone(cursor.getString(phoneIndex)); // created setPhone method in Doctor class
                        doctor.setEmail(cursor.getString(emailIndex)); // created setEmail method in Doctor class

                        AppDatabase.databaseWriteExecutor.execute(() -> {
                            try {
                                newDb.doctorDao().insert(doctor);
                            } catch (Exception e) {
                                Log.e("Migration", "Error inserting doctor", e);
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Migration", "Error processing doctor data", e);
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    private void migrateAppointments() {
        Cursor cursor = oldDb.query("appointments", null, null, null, null, null, null);
        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex("id");
                int patientIdIndex = cursor.getColumnIndex("patient_id");
                int doctorIdIndex = cursor.getColumnIndex("doctor_id");
                int dateIndex = cursor.getColumnIndex("appointment_date");
                int statusIndex = cursor.getColumnIndex("status");

                if (idIndex < 0 || patientIdIndex < 0 || doctorIdIndex < 0 || 
                    dateIndex < 0 || statusIndex < 0) {
                    Log.e("Migration", "One or more columns missing in appointments table");
                    return;
                }

                while (cursor.moveToNext()) {
                    try {
                        final Appointment appointment = new Appointment();
                        
                        if (!cursor.isNull(idIndex)) {
                            appointment.setId(cursor.getInt(idIndex));
                        }
                        if (!cursor.isNull(patientIdIndex)) {
                            appointment.setPatientId(cursor.getInt(patientIdIndex));
                        }
                        if (!cursor.isNull(doctorIdIndex)) {
                            appointment.setDoctorId(cursor.getInt(doctorIdIndex));
                        }
                        if (!cursor.isNull(dateIndex)) {
                            appointment.setAppointmentDate(new java.sql.Date(cursor.getLong(dateIndex)));
                        }
                        appointment.setStatus(cursor.getString(statusIndex));

                        AppDatabase.databaseWriteExecutor.execute(() -> {
                            try {
                                newDb.appointmentDao().insert(appointment);
                            } catch (Exception e) {
                                Log.e("Migration", "Error inserting appointment", e);
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Migration", "Error processing appointment data", e);
                        continue;
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    private void migrateSchedules() {
        Cursor cursor = oldDb.query("schedules", null, null, null, null, null, null);
        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex("id");
                int doctorIdIndex = cursor.getColumnIndex("doctor_id");
                int dayOfWeekIndex = cursor.getColumnIndex("day_of_week");
                int startTimeIndex = cursor.getColumnIndex("start_time");
                int endTimeIndex = cursor.getColumnIndex("end_time");

                if (idIndex < 0 || doctorIdIndex < 0 || dayOfWeekIndex < 0 || 
                    startTimeIndex < 0 || endTimeIndex < 0) {
                    Log.e("Migration", "One or more columns missing in schedules table");
                    return;
                }

                while (cursor.moveToNext()) {
                    try {
                        final Schedule schedule = new Schedule();
                        
                        if (!cursor.isNull(idIndex)) {
                            schedule.setId(cursor.getInt(idIndex));
                        }
                        if (!cursor.isNull(doctorIdIndex)) {
                            schedule.setDoctorId(cursor.getInt(doctorIdIndex));
                        }
                        schedule.setDayOfWeek(cursor.getString(dayOfWeekIndex));
                        schedule.setStartTime(cursor.getString(startTimeIndex));
                        schedule.setEndTime(cursor.getString(endTimeIndex));

                        AppDatabase.databaseWriteExecutor.execute(() -> {
                            try {
                                newDb.scheduleDao().insert(schedule);
                            } catch (Exception e) {
                                Log.e("Migration", "Error inserting schedule", e);
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Migration", "Error processing schedule data", e);
                        continue;
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    private void migrateNotifications() {
        Cursor cursor = oldDb.query("notifications", null, null, null, null, null, null);
        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex("id");
                int userIdIndex = cursor.getColumnIndex("user_id");
                int messageIndex = cursor.getColumnIndex("message");
                int isReadIndex = cursor.getColumnIndex("is_read");
                int timestampIndex = cursor.getColumnIndex("timestamp");

                // Verify all columns exist
                if (idIndex < 0 || userIdIndex < 0 || messageIndex < 0 || 
                    isReadIndex < 0 || timestampIndex < 0) {
                    Log.e("Migration", "One or more columns missing in notifications table");
                    return;
                }

                while (cursor.moveToNext()) {
                    try {
                        final Notification notification = new Notification();
                        
                        // Safely get values with null checks
                        if (!cursor.isNull(idIndex)) {
                            notification.setId(cursor.getInt(idIndex));
                        }
                        if (!cursor.isNull(userIdIndex)) {
                            notification.setUserId(cursor.getInt(userIdIndex));
                        }
                        notification.setMessage(cursor.getString(messageIndex));
                        notification.setRead(cursor.getInt(isReadIndex) == 1);
                        if (!cursor.isNull(timestampIndex)) {
                            notification.setTimestamp(cursor.getLong(timestampIndex));
                        }

                        AppDatabase.databaseWriteExecutor.execute(() -> {
                            try {
                                newDb.notificationDao().insert(notification);
                            } catch (Exception e) {
                                Log.e("Migration", "Error inserting notification", e);
                            }
                        });
                    } catch (Exception e) {
                        Log.e("Migration", "Error processing notification data", e);
                        continue; // Skip this record and continue with the next one
                    }
                }
            } finally {
                cursor.close();
            }
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
// cap nhat
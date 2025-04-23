package com.example.shms.utils;

public class Constants {
    // Roles
    public static final String ROLE_DOCTOR = "DOCTOR";
    public static final String ROLE_PATIENT = "PATIENT";
    public static final String ROLE_STAFF = "STAFF";

    // Bed states
    public static final int BED_STATE_AVAILABLE = 1;
    public static final int BED_STATE_MAINTENANCE = 2;
    public static final int BED_STATE_OCCUPIED = 3;

    // Departments
    public static final String DEPT_INTERNAL = "Khoa nội";
    public static final String DEPT_SURGERY = "Khoa ngoại";
    public static final String DEPT_NEUROLOGY = "Khoa Thần Kinh";

    // SharedPreferences Keys
    public static final String PREF_USER_ID = "user_id";
    public static final String PREF_USERNAME = "username";
    public static final String PREF_ROLE = "role";
    public static final String PREF_REMEMBER_ME = "remember_me";

    // Database Table Names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String TABLE_MEDICINES = "medicines";
    public static final String TABLE_PRESCRIPTIONS = "prescriptions";
    public static final String TABLE_BEDS = "beds";
}
package com.example.shms;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    private static final String PREF_NAME = "HospitalAppPref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String username, String password, boolean rememberMe) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_REMEMBER_ME, rememberMe);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit(); // Sử dụng commit() thay vì apply() để đảm bảo lưu ngay lập tức

        Log.d("SessionManager", "Login session created for user: " + username);
        Log.d("SessionManager", "Remember me: " + rememberMe);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public boolean isRememberMeEnabled() {
        return pref.getBoolean(KEY_REMEMBER_ME, false);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    public String getPassword() {
        return pref.getString(KEY_PASSWORD, "");
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Log.d("SessionManager", "User logged out");
    }

    public void saveCredentials(String username, String password) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
        Log.d("SessionManager", "Credentials saved for user: " + username);
    }
}

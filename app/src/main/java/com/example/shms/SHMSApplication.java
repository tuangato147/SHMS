// File: SHMSApplication.java

package com.example.shms;

import android.app.Application;
import android.preference.PreferenceManager;

import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.database.DataIntegrityChecker;
import com.example.shms.data.local.database.MigrationHelper;
import com.example.shms.data.local.migration.DatabaseMigrationManager;

/**
 * Class Application chính của ứng dụng
 * Khởi tạo các thành phần cốt lõi của ứng dụng
 */
public class SHMSApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Khởi tạo Room Database
        AppDatabase.getDatabase(this);

        // Thực hiện migration nếu cần
        new Thread(() -> {
            DatabaseMigrationManager migrationManager = new DatabaseMigrationManager(this);
            migrationManager.startMigration();
        }).start();

        // Kiểm tra xem đã migrate chưa
        if (!PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("DATA_MIGRATED", false)) {

            migrateData();
        }
    }

    private void migrateData() {
        String oldDbPath = getDatabasePath("old_database.db").getAbsolutePath();
        MigrationHelper migrationHelper = new MigrationHelper(this, oldDbPath);

        // Thực hiện migration trong background thread
        new Thread(() -> {
            migrationHelper.migrateData();

            // Verify data
            DataIntegrityChecker checker = new DataIntegrityChecker(this);
            checker.verifyDataIntegrity();

            // Đánh dấu đã migrate xong
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putBoolean("DATA_MIGRATED", true)
                    .apply();
        }).start();
    }
}
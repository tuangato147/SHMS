package com.example.shms;

import static org.hamcrest.MatcherAssert.assertThat;

import android.database.Cursor;
import static org.junit.Assert.assertEquals;

import androidx.room.migration.Migration;
import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.database.Migration_1_2;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {
    private static final String TEST_DB = "migration-test";

    @Rule
    public MigrationTestHelper helper;

    // Định nghĩa Migration (thêm vào AppDatabase.java)
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Logic migration ở đây
            database.execSQL("CREATE TABLE IF NOT EXISTS `users_new` " +
                    "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`username` TEXT NOT NULL, " +
                    "`password` TEXT NOT NULL, " +
                    "`role` TEXT NOT NULL)");

            // Copy dữ liệu từ bảng cũ (nếu có)
            database.execSQL("INSERT INTO users_new (id, username, password, role) " +
                    "SELECT id, username, password, role FROM users");

            // Xóa bảng cũ
            database.execSQL("DROP TABLE IF EXISTS users");

            // Đổi tên bảng mới
            database.execSQL("ALTER TABLE users_new RENAME TO users");
        }
    };
    @Before
    public void setUp() {
        // Khởi tạo MigrationTestHelper
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                AppDatabase.class.getCanonicalName(),
                new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void migrate1To2() throws IOException {
        // Tạo database version 1
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);

        // Thêm dữ liệu test cho version 1
        db.execSQL("INSERT INTO users (username, password, role) VALUES ('test', 'password', 'USER')");

        db.close();

        // Kiểm tra migration từ version 1 sang 2
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, AppDatabase.MIGRATION_1_2);

        // Kiểm tra dữ liệu sau migration
        Cursor cursor = db.query("SELECT * FROM users WHERE username = 'test'");
        assertThat(cursor.getCount()).isEqualTo(1);
        cursor.close();
    }
}

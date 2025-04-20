package com.example.shms.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_1_2 extends Migration {
    public Migration_1_2() {
        super(1, 2);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        // Tạo bảng mới với schema Room
        database.execSQL("CREATE TABLE IF NOT EXISTS users_new " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "role TEXT NOT NULL, " +
                "full_name TEXT, " +
                "email TEXT, " +
                "phone TEXT)");

        // Copy dữ liệu từ bảng cũ sang bảng mới
        database.execSQL("INSERT INTO users_new (id, username, password, role, full_name, email, phone) " +
                "SELECT id, username, password, role, full_name, email, phone FROM users");

        // Xóa bảng cũ
        database.execSQL("DROP TABLE IF EXISTS users");

        // Đổi tên bảng mới thành tên bảng cũ
        database.execSQL("ALTER TABLE users_new RENAME TO users");
    }
}

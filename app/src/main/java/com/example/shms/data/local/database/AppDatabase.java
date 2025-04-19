// File: data/local/database/AppDatabase.java

package com.example.shms.data.local.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.shms.data.local.entities.User;
// Import các entity khác khi cần

/**
 * Class chính để quản lý database của ứng dụng
 * Sử dụng Room Database với các entity đã định nghĩa
 */
@Database(
        entities = {User.class}, // Thêm các entity khác vào đây
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    // Singleton instance
    private static AppDatabase INSTANCE;

    /**
     * Phương thức để lấy instance của database
     * Sử dụng Singleton pattern để đảm bảo chỉ có một instance
     */
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Tạo database
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "shms_database" // Tên file database
                            )
                            .fallbackToDestructiveMigration() // Xóa và tạo lại DB khi có thay đổi version
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Định nghĩa các DAO (Data Access Object)
    // Sẽ thêm sau trong Phase 2
}
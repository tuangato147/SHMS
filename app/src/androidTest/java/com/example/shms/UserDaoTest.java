package com.example.shms;

import static org.junit.Assert.assertEquals; // Thêm import này

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.shms.data.local.dao.UserDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private UserDao userDao;
    private AppDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndGetUser() throws Exception {
        // Tạo user test
        User user = new User();
        user.setUsername("test");
        user.setPassword("password");
        user.setRole("ROLE_USER");

        // Insert user
        long userId = userDao.insert(user);

        // Lấy user từ database
        User loadedUser = TestUtil.getValue(userDao.getUserById((int)userId));

        // Kiểm tra dữ liệu
        assertEquals("test", loadedUser.getUsername());
        assertEquals("ROLE_USER", loadedUser.getRole());
    }
}
package com.example.shms;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.shms.data.local.dao.UserDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.database.Migration_1_2;
import com.example.shms.data.local.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private UserDao userDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndGetUser() throws Exception {
        User user = new User("test", "password", "ROLE_USER");
        userDao.insert(user);
        LiveData<User> userById = userDao.getUserById(1);
        // Test assertions
    }
}
package com.hristiyantodorov.weatherapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.hristiyantodorov.weatherapp.model.AppDatabase;
import com.hristiyantodorov.weatherapp.persistence.user.UserDao;
import com.hristiyantodorov.weatherapp.persistence.user.UserDbModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UserDatabaseTests {
    private UserDao userDao;
    private AppDatabase testDatabase;


    @Before
    public void createDB() {
        Context context = App.getInstance().getApplicationContext();
        testDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = testDatabase.userDao();
    }

    @After
    public void closeDB() throws IOException {
        testDatabase.close();
    }

    @Test
    public void When_InsertUsers_Expect_DatabaseNotEmpty() throws Exception {
        userDao.insertUsers(new UserDbModel("test1.com"),
                new UserDbModel("test2.com"),
                new UserDbModel("test3.com"));
        List<UserDbModel> users = userDao.getAllUsers();
        assert (!users.isEmpty());
    }

    @Test
    public void When_deleteUser_Expect_DatabaseEmpty() throws Exception{
        UserDbModel testUser = new UserDbModel("testUser");
        userDao.insertUsers(testUser);
        List<UserDbModel> usersBeforeDelete = userDao.getAllUsers();
        userDao.deleteUserById(1);
        List<UserDbModel> usersAfterDelete = userDao.getAllUsers();
        assert (!usersBeforeDelete.isEmpty() && usersAfterDelete.isEmpty());
    }
}

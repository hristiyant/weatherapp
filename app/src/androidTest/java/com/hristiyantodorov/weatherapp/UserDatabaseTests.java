package com.hristiyantodorov.weatherapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.hristiyantodorov.weatherapp.model.AppDatabase;
import com.hristiyantodorov.weatherapp.persistence.user.UserDao;
import com.hristiyantodorov.weatherapp.persistence.user.UserDbModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void closeDB() {
        testDatabase.close();
    }

    @Test
    public void When_InsertUsers_Expect_DatabaseNotEmpty() {
        userDao.insertUsers(new UserDbModel("test1.com"),
                new UserDbModel("test2.com"),
                new UserDbModel("test3.com"));
        List<UserDbModel> users = userDao.getAllUsers();
        Assert.assertFalse(users.isEmpty());
    }

    @Test
    public void When_deleteUser_Expect_DatabaseEmpty() {
        UserDbModel testUser = new UserDbModel("testUser");
        userDao.insertUsers(testUser);
        List<UserDbModel> usersBeforeDelete = userDao.getAllUsers();
        userDao.deleteUsers(usersBeforeDelete.get(0));
        List<UserDbModel> usersAfterDelete = userDao.getAllUsers();
        Assert.assertTrue(!usersBeforeDelete.isEmpty() && usersAfterDelete.isEmpty());
    }

    @Test
    public void When_GetUserByEmail_Expect_NotNull() {
        UserDbModel testUser = new UserDbModel("testEmail");
        userDao.insertUsers(testUser);
        UserDbModel testUserToCompare = userDao.getUserByEmail("testEmail");
        Assert.assertNotNull(testUserToCompare);
    }
}

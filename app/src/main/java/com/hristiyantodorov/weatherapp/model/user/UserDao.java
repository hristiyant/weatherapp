package com.hristiyantodorov.weatherapp.model.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE email LIKE :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE id LIKE :id LIMIT 1")
    User getUserById(int id);

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);
}

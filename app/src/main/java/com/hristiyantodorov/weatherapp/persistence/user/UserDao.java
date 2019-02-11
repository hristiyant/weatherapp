package com.hristiyantodorov.weatherapp.persistence.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<UserDbModel> getAllUsers();

    @Query("SELECT * FROM users WHERE email = :email")
    UserDbModel getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE id = :id")
    UserDbModel getUserById(int id);

    @Insert
    void insertUsers(UserDbModel... users);

    @Delete
    void deleteUsers(UserDbModel... users);

    @Query("DELETE FROM users WHERE id = :userId")
    void deleteUserById(int userId);
}

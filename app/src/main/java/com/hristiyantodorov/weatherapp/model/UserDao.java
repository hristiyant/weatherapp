package com.hristiyantodorov.weatherapp.model;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email LIKE :email LIMIT 1")
    User getUserByEmail(String email);

    @Insert
    void insert(User user);
}

package com.hristiyantodorov.weatherapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hristiyantodorov.weatherapp.model.location.Location;
import com.hristiyantodorov.weatherapp.model.location.LocationDao;
import com.hristiyantodorov.weatherapp.model.user.User;
import com.hristiyantodorov.weatherapp.model.user.UserDao;

@Database(entities = {
        User.class,
        Location.class},
        version = 1)
// TODO: 2/5/2019 Add type converters
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract LocationDao locationDao();
}
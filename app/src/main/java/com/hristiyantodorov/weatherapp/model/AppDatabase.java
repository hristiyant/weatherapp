package com.hristiyantodorov.weatherapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hristiyantodorov.weatherapp.model.location.LocationDao;
import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.model.user.UserDao;
import com.hristiyantodorov.weatherapp.model.user.UserDbModel;

@Database(entities = {
        UserDbModel.class,
        LocationDbModel.class},
        version = 1)
//@TypeConverters({PersistenceTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract LocationDao locationDao();
}
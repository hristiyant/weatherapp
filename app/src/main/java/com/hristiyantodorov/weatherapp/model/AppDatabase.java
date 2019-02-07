package com.hristiyantodorov.weatherapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.hristiyantodorov.weatherapp.persistence.location.LocationDao;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.persistence.user.UserDao;
import com.hristiyantodorov.weatherapp.persistence.user.UserDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceTypeConverter;

@Database(entities = {
        UserDbModel.class,
        LocationDbModel.class},
        version = 1)
@TypeConverters({PersistenceTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract LocationDao locationDao();
}
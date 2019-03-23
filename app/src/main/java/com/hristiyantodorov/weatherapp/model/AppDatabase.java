package com.hristiyantodorov.weatherapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDao;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDao;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDao;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDao;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastHourlyDao;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastHourlyDbModel;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDao;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.model.database.user.UserDao;
import com.hristiyantodorov.weatherapp.model.database.user.UserDbModel;

@Database(entities = {
        UserDbModel.class,
        LocationDbModel.class,
        ForecastFullDbModel.class,
        ForecastCurrentlyDbModel.class,
        ForecastHourlyDbModel.class,
        ForecastDailyDbModel.class,
        ForecastDailyDataDbModel.class},
        version = 1)
//@TypeConverters({PersistenceTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract LocationDao locationDao();

    public abstract ForecastFullDao forecastFullDao();

    public abstract ForecastCurrentlyDao forecastCurrentlyDao();

    public abstract ForecastHourlyDao forecastHourlyDao();

    public abstract ForecastDailyDao forecastDailyDao();

    public abstract ForecastDailyDataDao forecastDailyDataDao();
}
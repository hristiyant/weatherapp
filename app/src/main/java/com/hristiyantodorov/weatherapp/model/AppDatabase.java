package com.hristiyantodorov.weatherapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDao;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastDailyDao;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastDailyDataDao;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastDailyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDao;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastHourlyDao;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastHourlyDbModel;
import com.hristiyantodorov.weatherapp.model.location.LocationDao;
import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.model.user.UserDao;
import com.hristiyantodorov.weatherapp.model.user.UserDbModel;

@Database(entities = {
        UserDbModel.class,
        LocationDbModel.class,
        ForecastFullDbModel.class,
        ForecastCurrentlyDbModel.class,
        ForecastHourlyDbModel.class,
        ForecastDailyDbModel.class,
        ForecastDailyDataDbModel.class},
        version = 1,
        exportSchema = false)
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
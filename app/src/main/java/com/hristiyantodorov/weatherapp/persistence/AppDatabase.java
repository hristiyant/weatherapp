package com.hristiyantodorov.weatherapp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDao;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastHourlyDbModel;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDao;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;

@Database(entities = {
        LocationDbModel.class,
        ForecastFullDbModel.class,
        ForecastCurrentlyDbModel.class,
        ForecastHourlyDbModel.class,
        ForecastDailyDbModel.class,
        ForecastDailyDataDbModel.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LocationDao locationDao();

    public abstract ForecastFullDao forecastFullDao();
}
package com.hristiyantodorov.weatherapp.networking.services;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDao;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;

public class WeatherApiService {

    private ForecastFullDao forecastFullDao;
    private static WeatherApiService instance;
    private static final Object LOCK = new Object();

    private WeatherApiService(ForecastFullDao forecastFullDao) {
        this.forecastFullDao = forecastFullDao;
    }

    public static synchronized WeatherApiService getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new WeatherApiService(
                            PersistenceDatabase.getAppDatabase(context).forecastFullDao()
                    );
                }
            }
        }
        return instance;
    }
}
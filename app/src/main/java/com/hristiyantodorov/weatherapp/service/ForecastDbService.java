package com.hristiyantodorov.weatherapp.service;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDao;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;

public class ForecastDbService {

    private ForecastFullDao forecastFullDao;
    private static ForecastDbService instance;
    private static final Object LOCK = new Object();

    private ForecastDbService(ForecastFullDao forecastFullDao) {
        this.forecastFullDao = forecastFullDao;
    }

    public static synchronized ForecastDbService getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new ForecastDbService(
                            PersistenceDatabase.getAppDatabase(context).forecastFullDao()
                    );
                }
            }
        }
        return instance;
    }
}
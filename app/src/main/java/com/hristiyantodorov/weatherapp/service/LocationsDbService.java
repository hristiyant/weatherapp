package com.hristiyantodorov.weatherapp.service;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.location.LocationDao;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;

import java.util.List;

import io.reactivex.Single;

public class LocationsDbService {

    private LocationDao locationDao;
    private static LocationsDbService instance;
    private static final Object LOCK = new Object();

    private LocationsDbService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    public static synchronized LocationsDbService getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new LocationsDbService(
                            PersistenceDatabase.getAppDatabase(context).locationDao()
                    );
                }
            }
        }
        return instance;
    }

    public Single<List<LocationDbModel>> getAllLocationsList() {
        return locationDao.getAllLocations();
    }

    public Single<Integer> checkForNullIcons() {
        return locationDao.countNulls();
    }

    public List<LocationDbModel> getFilteredLocationsList(String pattern) {
        return locationDao.getLocationsByName(pattern);
    }

    public void update(LocationDbModel locationDbModel) {
        locationDao.update(locationDbModel);
    }
}
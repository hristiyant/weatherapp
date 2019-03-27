package com.hristiyantodorov.weatherapp.networking.services;

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
    /*private static final List<LocationDbModel> defaultLocationsList = Arrays.asList(
            new LocationDbModel("Tokyo", 35.652832, 139.839478),
            new LocationDbModel("New York", 40.730610, -73.935242),
            new LocationDbModel("Paris", 48.864716, 2.349014),
            new LocationDbModel("London", 51.509865, -0.118092),
            new LocationDbModel("Sydney", -33.865143, 151.209900),
            new LocationDbModel("Sliven", 42.68583, 26.32917),
            new LocationDbModel("Sofia", 42.69751, 23.32415)
    );*/

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

   /* public void populateDatabaseWithDefaultList() {
        locationDao.insertAll(defaultLocationsList);
    }*/

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
package com.hristiyantodorov.weatherapp.service;

import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDao;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class LocationsDbService {

    private LocationDao locationDao;

    @Inject
    public LocationsDbService(PersistenceDatabase persistenceDatabase) {
        this.locationDao = persistenceDatabase.getLocationDao();
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
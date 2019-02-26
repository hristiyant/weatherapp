package com.hristiyantodorov.weatherapp.repository;

import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;

import java.util.List;

public interface LocationRepository {

    LocationDbModel getLocationByName(String name);

    LocationDbModel getLocationById(String id);

    void insertLocation(LocationDbModel locationDbModel);

    void insertLocations(List<LocationDbModel> locationDbModels);
}

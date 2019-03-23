package com.hristiyantodorov.weatherapp.repositories;

import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;

import java.util.List;

public interface LocationRepository {

    LocationDbModel getLocationByName(String name);

    LocationDbModel getLocationById(String id);

    void insertLocation(LocationDbModel locationDbModel);

    void insertLocations(List<LocationDbModel> locationDbModels);
}

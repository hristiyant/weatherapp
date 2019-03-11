package com.hristiyantodorov.weatherapp.repository;

import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.util.AsyncResponse;

import java.util.List;

public interface LocationRepository {

    List<LocationDbModel> getAllLocations(AsyncResponse response);

    LocationDbModel getLocationByName(String name, AsyncResponse response);

    LocationDbModel getLocationById(int id, AsyncResponse response);

    void insertLocations(LocationDbModel... locations);

    void updateLocations(LocationDbModel... locations);

    void deleteLocations(LocationDbModel... locations);

}

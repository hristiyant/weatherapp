package com.hristiyantodorov.weatherapp.persistence.location;


import java.util.List;

public interface LocationRepository {
    List<LocationDbModel> getAllLocations();

    LocationDbModel getLocationByName(String name);

    LocationDbModel getLocationById(int id);

    void insertLocations(LocationDbModel... locations);

    void updateLocations(LocationDbModel... locations);

    void deleteLocations(LocationDbModel... locations);
}

package com.hristiyantodorov.weatherapp.persistence.location;


public interface LocationRepository {

    LocationDbModel getLocationByName(String name);

    LocationDbModel getLocationById(int id);

    void insertLocation(LocationDbModel... locations);

    void deleteLocation(Integer... ids);
}

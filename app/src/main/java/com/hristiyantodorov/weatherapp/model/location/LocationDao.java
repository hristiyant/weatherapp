package com.hristiyantodorov.weatherapp.model.location;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM locations")
    List<Location> getAllLocations();

    @Query("SELECT * FROM locations WHERE id LIKE :id LIMIT 1")
    Location getLocationById(int id);

    @Query("SELECT * FROM locations WHERE name LIKE :name LIMIT 1")
    Location getLocationByName(String name);

    @Insert
    void insert(Location location);
}

package com.hristiyantodorov.weatherapp.persistence.location;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM locations")
    List<LocationDbModel> getAllLocations();

    @Query("SELECT * FROM locations WHERE id = :id")
    LocationDbModel getLocationById(int id);

    @Query("SELECT * FROM locations WHERE name = :name")
    LocationDbModel getLocationByName(String name);

    @Insert
    void insertLocation(LocationDbModel locationDbModel);

    @Delete
    void deleteLocation(int id);
}

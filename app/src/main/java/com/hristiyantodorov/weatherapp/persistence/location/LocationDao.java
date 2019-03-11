package com.hristiyantodorov.weatherapp.persistence.location;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM locations")
    List<LocationDbModel> getAllLocations();

    @Query("SELECT * FROM locations WHERE name = :name")
    LocationDbModel getLocationByName(String name);

    @Query("SELECT * FROM locations WHERE id = :id")
    LocationDbModel getLocationById(int id);

    @Insert
    void insertLocations(LocationDbModel... locations);

    @Update
    void updateLocations(LocationDbModel... locations);

    @Delete
    void deleteLocations(LocationDbModel... locations);

}

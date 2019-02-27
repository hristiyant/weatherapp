package com.hristiyantodorov.weatherapp.model.location;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM locations")
    List<LocationDbModel> getAllLocations();

    @Query("SELECT * FROM locations WHERE id = :id")
    LocationDbModel getLocationById(int id);

    @Query("SELECT * FROM locations WHERE name LIKE '%' || :name || '%'")
    List<LocationDbModel> getLocationsByName(String name);

    @Insert
    void insert(LocationDbModel locationDbModel);

    @Insert
    void insertAll(List<LocationDbModel> locationDbModels);

}

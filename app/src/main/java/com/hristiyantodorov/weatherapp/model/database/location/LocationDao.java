package com.hristiyantodorov.weatherapp.model.database.location;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM locations")
    Single<List<LocationDbModel>> getAllLocations();

    @Query("SELECT * FROM locations WHERE id = :id")
    LocationDbModel getLocationById(int id);

    @Query("SELECT * FROM locations WHERE name LIKE '%' || :name || '%'")
    List<LocationDbModel> getLocationsByName(String name);

    @Query("SELECT COUNT(id) FROM locations WHERE icon IS NULL")
    Single<Integer> countNulls();

    @Insert
    void insert(LocationDbModel locationDbModel);

    @Insert
    void insertAll(List<LocationDbModel> locationDbModels);

    @Update
    void update(LocationDbModel locationDbModel);
}

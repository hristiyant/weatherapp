package com.hristiyantodorov.weatherapp.model.forecast;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface ForecastFullDao {

    @Insert
    void insert(ForecastFullDbModel... forecastFullDbModels);

    @Update
    void update(ForecastFullDbModel... forecastFullDbModels);

    @Delete
    void delete(ForecastFullDbModel... forecastFullDbModels);

}

package com.hristiyantodorov.weatherapp.model.forecast;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ForecastCurrentlyDao {

    @Insert
    void insert(ForecastCurrentlyDbModel... forecastCurrentlyDbModels);

    @Update
    void update(ForecastCurrentlyDbModel... forecastCurrentlyDbModels);

    @Delete
    void delete(ForecastCurrentlyDbModel... forecastCurrentlyDbModels);

    @Query("SELECT * FROM forecast_currently")
    List<ForecastCurrentlyDbModel> getAllForecastCurrently();

    @Query("SELECT * FROM forecast_currently WHERE forecastHourlyId = :forecastHourlyId")
    List<ForecastCurrentlyDbModel> getAllForecastCurrentlyByHourlyId(final int forecastHourlyId);

    @Query("SELECT * FROM forecast_currently WHERE forecastFullId = :forecastFullId")
    List<ForecastCurrentlyDbModel> getAllForecastCurrentlyByFullId(final int forecastFullId);

}
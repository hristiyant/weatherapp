package com.hristiyantodorov.weatherapp.model.database.forecast;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ForecastCurrentlyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ForecastCurrentlyDbModel... models);

    @Update
    void update(ForecastCurrentlyDbModel... models);

    @Delete
    void delete(ForecastCurrentlyDbModel... models);

    @Query("SELECT * FROM forecast_currently")
    List<ForecastCurrentlyDbModel> getAllForecastCurrently();

    @Query("SELECT * FROM forecast_currently WHERE forecastHourlyId = :forecastHourlyId")
    List<ForecastCurrentlyDbModel> getAllForecastCurrentlyByHourlyId(final int forecastHourlyId);

    @Query("SELECT * FROM forecast_currently WHERE forecastFullId = :forecastFullId")
    List<ForecastCurrentlyDbModel> getAllForecastCurrentlyByFullId(final int forecastFullId);
}
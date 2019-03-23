package com.hristiyantodorov.weatherapp.model.database.forecast;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ForecastHourlyDao {

    @Insert
    void insert(ForecastHourlyDbModel... models);

    @Update
    void update(ForecastHourlyDbModel... models);

    @Delete
    void delete(ForecastHourlyDbModel... models);

    @Query("SELECT * FROM forecast_hourly")
    List<ForecastHourlyDbModel> getAllForecastHourly();

    @Query("SELECT * FROM forecast_hourly WHERE forecastFullId = :forecastFullId")
    List<ForecastHourlyDbModel> getAllForecastHourlyByFullId(final int forecastFullId);

}
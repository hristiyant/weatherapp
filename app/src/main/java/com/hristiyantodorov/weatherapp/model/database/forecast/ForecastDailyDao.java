package com.hristiyantodorov.weatherapp.model.database.forecast;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ForecastDailyDao {

    @Insert
    void insert(ForecastDailyDbModel... models);

    @Update
    void update(ForecastDailyDbModel... models);

    @Delete
    void delete(ForecastDailyDbModel... models);

    @Query("SELECT * FROM forecast_daily")
    List<ForecastDailyDbModel> getAllForecastDaily();

    @Query("SELECT * FROM forecast_daily WHERE forecastFullId + :forecastFullId")
    List<ForecastDailyDbModel> getAllForecastDailyByFullId(final int forecastFullId);

}
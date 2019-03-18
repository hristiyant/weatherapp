package com.hristiyantodorov.weatherapp.model.forecast;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ForecastDailyDataDao {

    @Insert
    void insert(ForecastDailyDataDbModel... models);

    @Update
    void update(ForecastDailyDataDbModel... models);

    @Delete
    void delete(ForecastDailyDataDbModel... models);

    @Query("SELECT * FROM forecast_daily_data")
    List<ForecastDailyDataDbModel> getAllForecastDailyData();

    @Query("SELECT * FROM forecast_daily_data WHERE forecastDailyId = :forecastDailyId")
    List<ForecastDailyDataDbModel> getAllForecastDailyDataByDailyId(final int forecastDailyId);

}

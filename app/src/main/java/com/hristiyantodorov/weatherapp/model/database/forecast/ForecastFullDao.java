package com.hristiyantodorov.weatherapp.model.database.forecast;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class ForecastFullDao {

    @Query("SELECT * FROM forecast_data LIMIT 1")
    public abstract Single<ForecastFullDbModel> getForecastFullRx();

    @Query("SELECT * FROM forecast_data LIMIT 1")
    public abstract ForecastFullDbModel getForecastFull();

    @Query("SELECT * FROM forecast_currently WHERE forecastFullId = :id")
    public abstract Single<List<ForecastCurrentlyDbModel>> getForecastCurrentlyByFullId(long id);

    @Query("SELECT * FROM forecast_currently WHERE forecastHourlyId = :id")
    public abstract Single<List<ForecastCurrentlyDbModel>> getForecastHourlyDataByHourlyId(long id);

    @Query("SELECT * FROM forecast_hourly LIMIT 1")
    public abstract ForecastHourlyDbModel getForecastHourly();

    @Query("SELECT * FROM forecast_hourly WHERE forecastFullId = :id")
    public abstract Single<ForecastHourlyDbModel> getForecastHourlyById(long id);

    @Query("SELECT * FROM forecast_daily LIMIT 1")
    public abstract ForecastDailyDbModel getForecastDaily();

    @Query("SELECT * FROM forecast_daily WHERE forecastFullId = :id")
    public abstract Single<ForecastDailyDbModel> getForecastDailyById(long id);

    @Query("SELECT * FROM forecast_daily_data WHERE forecastDailyId = :id")
    public abstract Single<List<ForecastDailyDataDbModel>> getForecastDailyDataByDailyId(long id);

    @Query("SELECT COUNT(*) FROM forecast_data")
    public abstract int countForecastDataElements();

    /**
     * Updates forecast_data's only row if it has already been inserted.
     *
     * @param id
     * @param latitude
     * @param longitude
     * @param timezone
     * @param lastUpdatedTimestamp
     */
    @Query("UPDATE forecast_data SET" +
            " latitude= :latitude," +
            " longitude = :longitude," +
            " timezone = :timezone," +
            " lastUpdatedTimestamp = :lastUpdatedTimestamp " +
            "WHERE id = :id")
    public abstract void updateForecastFull(long id, double latitude, double longitude, String timezone, String lastUpdatedTimestamp);

    /**
     * Updates forecast_currently's only row if it has already been inserted.
     *
     * @param id
     * @param time
     * @param summary
     * @param icon
     * @param temperature
     * @param apparentTemperature
     * @param humidity
     * @param pressure
     * @param windSpeed
     */
    @Query("UPDATE forecast_currently SET" +
            " time = :time," +
            " summary = :summary," +
            " icon = :icon," +
            " temperature = :temperature," +
            " apparentTemperature =:apparentTemperature," +
            " humidity = :humidity," +
            " pressure = :pressure," +
            " windSpeed = :windSpeed" +
            " WHERE currentlyId = :id")
    public abstract void updateForecastCurrently(long id, String time, String summary, String icon, double temperature,
                                                 double apparentTemperature, double humidity, double pressure, double windSpeed);

    /**
     * Updates forecast_hourly's only row with forecastFullId != null
     * if it has already been inserted.
     *
     * @param id
     * @param summary
     * @param icon
     */
    @Query("UPDATE forecast_hourly SET" +
            " summary = :summary," +
            " icon = :icon" +
            " WHERE hourlyId = :id")
    public abstract void updateForecastHourly(long id, String summary, String icon);

    /**
     * Updates forecast_daily's only row if it has already been inserted.
     *
     * @param id
     * @param summary
     * @param icon
     */
    @Query("UPDATE forecast_daily SET" +
            " summary = :summary," +
            " icon = :icon" +
            " WHERE dailyId = :id")
    public abstract void updateForecastDaily(long id, String summary, String icon);

    /**
     * Deletes the entire Hourly data from db.
     */
    @Query("DELETE FROM forecast_currently WHERE forecastHourlyId IS NOT NULL")
    public abstract void dropForecastHourlyData();

    /**
     * Deletes the entire Daily data from db.
     */
    @Query("DELETE FROM forecast_daily_data")
    public abstract void dropForecastDailyData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(ForecastFullDbModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ForecastCurrentlyDbModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(ForecastDailyDbModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ForecastDailyDataDbModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(ForecastHourlyDbModel model);
}
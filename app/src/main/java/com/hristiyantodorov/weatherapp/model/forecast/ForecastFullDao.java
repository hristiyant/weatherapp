package com.hristiyantodorov.weatherapp.model.forecast;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public abstract class ForecastFullDao implements BaseDao<ForecastFullDbModel> {

    @Query("SELECT * FROM forecast_data LIMIT 1")
    public abstract ForecastFullDbModel getForecastFull();

    @Query("SELECT * FROM forecast_hourly LIMIT 1")
    public abstract ForecastHourlyDbModel getForecastHourly();

    @Query("SELECT * FROM forecast_daily LIMIT 1")
    public abstract ForecastDailyDbModel getForecastDaily();

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
    abstract void updateForecastFull(long id, double latitude, double longitude, String timezone, String lastUpdatedTimestamp);

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
    abstract void updateForecastCurrently(long id, String time, String summary, String icon, double temperature,
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
    abstract void updateForecastHourly(long id, String summary, String icon);

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
    abstract void updateForecastDaily(long id, String summary, String icon);

    //Deletes entire Hourly data
    @Query("DELETE FROM forecast_currently WHERE forecastHourlyId IS NOT NULL")
    abstract void dropForecastHourlyData();

    //Deletes entire Daily data
    @Query("DELETE FROM forecast_daily_data")
    abstract void dropForecastDailyData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract long _insert(ForecastFullDbModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insert(ForecastCurrentlyDbModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract long _insert(ForecastDailyDbModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void _insert(ForecastDailyDataDbModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract long _insert(ForecastHourlyDbModel model);

    private void insertForecastFull(ForecastFullDbModel fullModel, ForecastCurrentlyDbModel currentlyModel) {

        long id = _insert(fullModel);

        insertForecastCurrently(id, currentlyModel);
        insertForecastDaily(id, fullModel.getDailyDbModel(), fullModel.getDailyDataDbModels());
        insertForecastHourly(id, fullModel.getHourlyDbModel(), fullModel.getHourlyDataDbModels());
    }

    private void insertForecastCurrently(long fullId, ForecastCurrentlyDbModel currentlyModel) {
        currentlyModel.setForecastFullId(fullId);
        _insert(currentlyModel);
    }

    private void insertForecastHourly(long fullId, ForecastHourlyDbModel hourlyDbModel, List<ForecastCurrentlyDbModel> hourlyDataDbModels) {
        hourlyDbModel.setForecastFullId(fullId);
        long hourlyId = _insert(hourlyDbModel);
        insertForecastHourlyData(hourlyId, hourlyDataDbModels);
    }

    private void insertForecastHourlyData(long hourlyId, List<ForecastCurrentlyDbModel> hourlyDataDbModels) {
        for (ForecastCurrentlyDbModel hourlyDbModel : hourlyDataDbModels) {
            hourlyDbModel.setForecastHourlyId(hourlyId);
            _insert(hourlyDbModel);
        }
    }

    private void insertForecastDaily(long fullId, ForecastDailyDbModel dailyModel, List<ForecastDailyDataDbModel> dailyDataDbModels) {
        dailyModel.setForecastFullId(fullId);
        long dailyId = _insert(dailyModel);
        insertForecastDailyData(dailyId, dailyDataDbModels);
    }

    private void insertForecastDailyData(long dailyId, List<ForecastDailyDataDbModel> dailyDataDbModels) {
        for (ForecastDailyDataDbModel dailyDataDbModel : dailyDataDbModels) {
            dailyDataDbModel.setForecastDailyId(dailyId);
            _insert(dailyDataDbModel);
        }
    }

    public void updateDb(ForecastFullDbModel model) {
        if (countForecastDataElements() > 0) {
            updateForecastFull(model);
        } else {
            insertForecastFull(model, model.getCurrentlyDbModel());
        }
    }

    private void updateForecastFull(ForecastFullDbModel fullDbModel) {
        long forecastFullId = getForecastFull().getId();
        if (fullDbModel != null) {
            updateForecastFull(forecastFullId,
                    fullDbModel.getLatitude(),
                    fullDbModel.getLongitude(),
                    fullDbModel.getTimezone(),
                    fullDbModel.getLastUpdatedTimestamp()
            );
            updateDbWithCurrently(forecastFullId, fullDbModel.getCurrentlyDbModel());
        }

        ForecastHourlyDbModel hourlyDbModel = fullDbModel.getHourlyDbModel();
        if (hourlyDbModel != null) {
            updateDbWithHourly(fullDbModel);
        }

        ForecastDailyDbModel dailyDbModel = fullDbModel.getDailyDbModel();
        if (dailyDbModel != null) {
            updateDbWithDaily(fullDbModel);
        }
    }

    private void updateDbWithCurrently(long forecastFullId, ForecastCurrentlyDbModel currentlyModel) {
        updateForecastCurrently(forecastFullId,
                currentlyModel.getTime(),
                currentlyModel.getSummary(),
                currentlyModel.getIcon(),
                currentlyModel.getTemperature(),
                currentlyModel.getApparentTemperature(),
                currentlyModel.getHumidity(),
                currentlyModel.getPressure(),
                currentlyModel.getWindSpeed());
    }

    private void updateDbWithHourly(ForecastFullDbModel fullModel) {
        ForecastHourlyDbModel model = fullModel.getHourlyDbModel();
        long forecastHourlyId = getForecastHourly().getHourlyId();
        updateForecastHourly(forecastHourlyId, model.getSummary(), model.getIcon());
        updateForecastHourlyData(fullModel.getHourlyDataDbModels(), forecastHourlyId);
    }

    private void updateForecastHourlyData(List<ForecastCurrentlyDbModel> hourlyData, long forecastHourlyId) {
        //Delete all records that have forecastHourlyId != 0
        dropForecastHourlyData();

        //Insert new hourly data
        for (ForecastCurrentlyDbModel hourlyDbModel : hourlyData) {
            hourlyDbModel.setForecastHourlyId(forecastHourlyId);
            _insert(hourlyDbModel);
        }
    }

    private void updateDbWithDaily(ForecastFullDbModel fullModel) {
        ForecastDailyDbModel model = fullModel.getDailyDbModel();
        long forecastDailyId = getForecastDaily().getDailyId();
        updateForecastDaily(forecastDailyId, model.getSummary(), model.getIcon());
        updateForecastDailyData(fullModel.getDailyDataDbModels(), forecastDailyId);
    }

    private void updateForecastDailyData(List<ForecastDailyDataDbModel> dailyData, long forecastDailyId) {
        //Delete all records in forecast_daily_data
        dropForecastDailyData();

        //Insert new daily data
        for (ForecastDailyDataDbModel dailyDbModel : dailyData) {
            dailyDbModel.setForecastDailyId(forecastDailyId);
            _insert(dailyDbModel);
        }
    }

}
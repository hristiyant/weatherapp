package com.hristiyantodorov.weatherapp.model.database.forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "forecast_data")
public class ForecastFullDbModel {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private Double latitude;
    private Double longitude;
    private String timezone;
    private String lastUpdatedTimestamp;

    @Ignore
    private ForecastCurrentlyDbModel currentlyDbModel;
    @Ignore
    private ForecastHourlyDbModel hourlyDbModel;
    @Ignore
    private ForecastDailyDbModel dailyDbModel;
    @Ignore
    private List<ForecastCurrentlyDbModel> hourlyDataDbModels;
    @Ignore
    private List<ForecastDailyDataDbModel> dailyDataDbModels;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(String lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public ForecastCurrentlyDbModel getCurrentlyDbModel() {
        return currentlyDbModel;
    }

    public void setCurrentlyDbModel(ForecastCurrentlyDbModel currentlyDbModel) {
        this.currentlyDbModel = currentlyDbModel;
    }

    public ForecastHourlyDbModel getHourlyDbModel() {
        return hourlyDbModel;
    }

    public void setHourlyDbModel(ForecastHourlyDbModel hourlyDbModel) {
        this.hourlyDbModel = hourlyDbModel;
    }

    public ForecastDailyDbModel getDailyDbModel() {
        return dailyDbModel;
    }

    public void setDailyDbModel(ForecastDailyDbModel dailyDbModel) {
        this.dailyDbModel = dailyDbModel;
    }

    public List<ForecastCurrentlyDbModel> getHourlyDataDbModels() {
        return hourlyDataDbModels;
    }

    public void setHourlyDataDbModels(List<ForecastCurrentlyDbModel> hourlyDataDbModels) {
        this.hourlyDataDbModels = hourlyDataDbModels;
    }

    public List<ForecastDailyDataDbModel> getDailyDataDbModels() {
        return dailyDataDbModels;
    }

    public void setDailyDataDbModels(List<ForecastDailyDataDbModel> dailyDataDbModels) {
        this.dailyDataDbModels = dailyDataDbModels;
    }
}
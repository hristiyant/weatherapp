package com.hristiyantodorov.weatherapp.model.forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "forecast_daily_data",
        indices = @Index(
                value = "forecastDailyId",
                name = "forecastDailyId"
        ),
        foreignKeys = @ForeignKey(
                entity = ForecastDailyDbModel.class,
                parentColumns = "dailyId",
                childColumns = "forecastDailyId",
                onDelete = ForeignKey.CASCADE
        ))
public class ForecastDailyDataDbModel {

    @PrimaryKey(autoGenerate = true)
    private Long dailyDataId;
    private String time;
    private String summary;
    private String icon;
    private String sunriseTime;
    private String sunsetTime;
    private Double humidity;
    private Double pressure;
    private Double windSpeed;
    private Double temperatureMin;
    private String temperatureMinTime;
    private Double temperatureMax;
    private String temperatureMaxTime;
    private Long forecastDailyId;

    public Long getDailyDataId() {
        return dailyDataId;
    }

    public void setDailyDataId(Long dailyDataId) {
        this.dailyDataId = dailyDataId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public String getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public void setTemperatureMinTime(String temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public void setTemperatureMaxTime(String temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

    public Long getForecastDailyId() {
        return forecastDailyId;
    }

    public void setForecastDailyId(Long forecastDailyId) {
        this.forecastDailyId = forecastDailyId;
    }

}
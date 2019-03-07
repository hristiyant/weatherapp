package com.hristiyantodorov.weatherapp.model.forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "forecast_currently",
        foreignKeys = {
                @ForeignKey(
                        entity = ForecastHourlyDbModel.class,
                        parentColumns = "hourlyId",
                        childColumns = "forecastHourlyId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = ForecastFullDbModel.class,
                        parentColumns = "id",
                        childColumns = "forecastFullId",
                        onDelete = ForeignKey.CASCADE
                )
        })
public class ForecastCurrentlyDbModel {

    @PrimaryKey(autoGenerate = true)
    private int currentlyId;
    private Long time;
    private String summary;
    private String icon;
    private Double temperature;
    private Double apparentTemperature;
    private Double humidity;
    private Double pressure;
    private Double windSpeed;
    private int forecastFullId;
    private int forecastHourlyId;

    public int getCurrentlyId() {
        return currentlyId;
    }

    public void setCurrentlyId(int currentlyId) {
        this.currentlyId = currentlyId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
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

    public int getForecastFullId() {
        return forecastFullId;
    }

    public void setForecastFullId(int forecastFullId) {
        this.forecastFullId = forecastFullId;
    }

    public int getForecastHourlyId() {
        return forecastHourlyId;
    }

    public void setForecastHourlyId(int forecastHourlyId) {
        this.forecastHourlyId = forecastHourlyId;
    }

}

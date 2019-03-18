package com.hristiyantodorov.weatherapp.model.forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "forecast_currently",
        indices = {
                @Index(
                        value = "forecastHourlyId",
                        name = "idxForecastHourlyId"
                ),
                @Index(
                        value = "forecastFullId",
                        name = "idxForecastFullIdCurrently"
                )
        },
        foreignKeys = {
                @ForeignKey(
                        entity = ForecastHourlyDbModel.class,
                        parentColumns = "hourlyId",
                        childColumns = "forecastHourlyId",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = ForecastFullDbModel.class,
                        parentColumns = "id",
                        childColumns = "forecastFullId",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE
                )
        })
public class ForecastCurrentlyDbModel {

    @PrimaryKey(autoGenerate = true)
    public Long currentlyId;
    private String time;
    private String summary;
    private String icon;
    private Double temperature;
    private Double apparentTemperature;
    private Double humidity;
    private Double pressure;
    private Double windSpeed;
    private Long forecastFullId;
    private Long forecastHourlyId;

    public Long getCurrentlyId() {
        return currentlyId;
    }

    public void setCurrentlyId(Long currentlyId) {
        this.currentlyId = currentlyId;
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

    public Long getForecastFullId() {
        return forecastFullId;
    }

    public void setForecastFullId(Long forecastFullId) {
        this.forecastFullId = forecastFullId;
    }

    public Long getForecastHourlyId() {
        return forecastHourlyId;
    }

    public void setForecastHourlyId(Long forecastHourlyId) {
        this.forecastHourlyId = forecastHourlyId;
    }

}
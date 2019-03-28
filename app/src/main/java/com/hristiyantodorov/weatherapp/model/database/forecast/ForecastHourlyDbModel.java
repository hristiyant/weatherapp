package com.hristiyantodorov.weatherapp.model.database.forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "forecast_hourly",
        indices = @Index(
                value = "forecastFullId",
                name = "idxForecastFullIdHourly"
        ),
        foreignKeys = @ForeignKey(
                entity = ForecastFullDbModel.class,
                parentColumns = "id",
                childColumns = "forecastFullId"
        ))
public class ForecastHourlyDbModel {

    @PrimaryKey(autoGenerate = true)
    private Long hourlyId;
    private String summary;
    private String icon;
    private Long forecastFullId;

    public Long getHourlyId() {
        return hourlyId;
    }

    public void setHourlyId(Long hourlyId) {
        this.hourlyId = hourlyId;
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

    public Long getForecastFullId() {
        return forecastFullId;
    }

    public void setForecastFullId(Long forecastFullId) {
        this.forecastFullId = forecastFullId;
    }
}
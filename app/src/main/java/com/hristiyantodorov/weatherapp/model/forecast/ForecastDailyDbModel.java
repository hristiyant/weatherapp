package com.hristiyantodorov.weatherapp.model.forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "forecast_daily",
        foreignKeys = @ForeignKey(
                entity = ForecastFullDbModel.class,
                parentColumns = "id",
                childColumns = "forecastFullId"
        ))
public class ForecastDailyDbModel {

    @PrimaryKey(autoGenerate = true)
    private int dailyId;
    private String summary;
    private String icon;
    private int forecastFullId;

    public int getDailyId() {
        return dailyId;
    }

    public void setDailyId(int dailyDataId) {
        this.dailyId = dailyDataId;
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

    public int getForecastFullId() {
        return forecastFullId;
    }

    public void setForecastFullId(int forecastFullId) {
        this.forecastFullId = forecastFullId;
    }

}

package com.hristiyantodorov.weatherapp.model.database.forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "forecast_daily",
        indices = @Index(
                value = "forecastFullId",
                name = "idxForecastFullIdDaily"
        ),
        foreignKeys = @ForeignKey(
                entity = ForecastFullDbModel.class,
                parentColumns = "id",
                childColumns = "forecastFullId"
        ))
public class ForecastDailyDbModel {

    @PrimaryKey(autoGenerate = true)
    public Long dailyId;
    private String summary;
    private String icon;
    private Long forecastFullId;

    public Long getDailyId() {
        return dailyId;
    }

    public void setDailyId(Long dailyDataId) {
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

    public Long getForecastFullId() {
        return forecastFullId;
    }

    public void setForecastFullId(Long forecastFullId) {
        this.forecastFullId = forecastFullId;
    }

}
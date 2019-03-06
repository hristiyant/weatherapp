package com.hristiyantodorov.weatherapp.util.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class ForecastFullResponse {

    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("currently")
    private ForecastCurrentlyResponse currently;
    @SerializedName("hourly")
    private ForecastHourlyResponse hourly;
    @SerializedName("daily")
    private ForecastDailyResponse daily;
    @SerializedName("offset")
    private int offset;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public ForecastCurrentlyResponse getCurrently() {
        return currently;
    }

    public void setCurrently(ForecastCurrentlyResponse currently) {
        this.currently = currently;
    }

    public ForecastHourlyResponse getHourly() {
        return hourly;
    }

    public void setHourly(ForecastHourlyResponse hourly) {
        this.hourly = hourly;
    }

    public ForecastDailyResponse getDaily() {
        return daily;
    }

    public void setDaily(ForecastDailyResponse daily) {
        this.daily = daily;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}

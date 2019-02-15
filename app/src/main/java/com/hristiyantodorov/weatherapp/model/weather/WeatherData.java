package com.hristiyantodorov.weatherapp.model.weather;

import java.io.Serializable;

/**
 * Model class for storing the entire data received from the API
 */
public class WeatherData implements Serializable {
    private double latitude;
    private double longitude;
    private String timezone;
    private WeatherDataCurrently currently;

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

    public WeatherDataCurrently getCurrently() {
        return currently;
    }

    public void setCurrently(WeatherDataCurrently currently) {
        this.currently = currently;
    }
}

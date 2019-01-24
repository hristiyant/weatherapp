package com.hristiyantodorov.weatherapp.models;

import android.graphics.drawable.Drawable;

public class WeatherDetailsData {
    public String conditions;
    public String temperature;
    public Drawable drawable;

    public WeatherDetailsData(String conditions, String temperature, Drawable drawable) {
        this.conditions = conditions;
        this.temperature = temperature;
        this.drawable = drawable;
    }
}

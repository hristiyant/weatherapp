package com.hristiyantodorov.weatherapp.models;

public class WeatherDetailsData {
    private String conditions;
    private String temperature;
    private int drawable;

    public WeatherDetailsData(String conditions, String temperature, int drawable) {
        this.conditions = conditions;
        this.temperature = temperature;
        this.drawable = drawable;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}

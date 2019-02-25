package com.hristiyantodorov.weatherapp.model.weather;

import java.util.List;

public class ForecastDataHourly extends ForecastData{

    private List<WeatherDataCurrently> data;

    public List<WeatherDataCurrently> getData() {
        return data;
    }

    public void setData(List<WeatherDataCurrently> data) {
        this.data = data;
    }
}

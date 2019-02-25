package com.hristiyantodorov.weatherapp.model.weather;

import java.util.List;

public class ForecastDataDaily extends ForecastData {

    private List<WeatherDataDaily> data;

    public List<WeatherDataDaily> getData() {
        return data;
    }

    public void setData(List<WeatherDataDaily> data) {
        this.data = data;
    }
}

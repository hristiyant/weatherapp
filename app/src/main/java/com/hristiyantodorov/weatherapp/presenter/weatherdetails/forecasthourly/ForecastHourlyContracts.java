package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import com.hristiyantodorov.weatherapp.model.weather.WeatherDataCurrently;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import java.util.List;

public interface ForecastHourlyContracts {
    interface View extends BaseView<Presenter> {
        void showForecastHourlyData(List<WeatherDataCurrently> hourlyData);
    }

    interface Presenter extends BasePresenter {
        void loadForecastHourlyData();
    }
}
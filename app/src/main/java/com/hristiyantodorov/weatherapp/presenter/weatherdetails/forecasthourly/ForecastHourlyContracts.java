package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import com.hristiyantodorov.weatherapp.model.weather.WeatherDataCurrently;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.temp.BasePresenter;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.temp.BaseView;

import java.util.List;

public interface ForecastHourlyContracts {
    interface View extends BaseView<Presenter> {
        void showForecastHourlyData(List<WeatherDataCurrently> hourlyData);
    }

    interface Presenter extends BasePresenter {
        void loadForecastHourlyData();
    }
}
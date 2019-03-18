package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import com.hristiyantodorov.weatherapp.presenter.BaseView;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastCurrentlyResponse;

import java.util.List;

public interface ForecastHourlyContracts {

    interface View extends BaseView<Presenter> {

        void showForecastHourlyData(List<ForecastCurrentlyResponse> hourlyData);

    }

    interface Presenter {

        void loadForecastHourlyData();

    }
}
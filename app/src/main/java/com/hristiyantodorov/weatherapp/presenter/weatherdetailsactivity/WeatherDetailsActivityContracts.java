package com.hristiyantodorov.weatherapp.presenter.weatherdetailsactivity;

import com.hristiyantodorov.weatherapp.model.response.ForecastCurrentlyResponse;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

public interface WeatherDetailsActivityContracts {

    interface View extends BaseView<Presenter> {

        void showForecast(ForecastCurrentlyResponse data, String timezone);
    }

    interface Presenter {

        void downloadForecastFromApi();

        String getTimestamp();

        void clearDisposables();
    }
}

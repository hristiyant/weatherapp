package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

public interface WeatherDetailsActivityContracts {
    interface View extends BaseView<Presenter> {
        void showForecast(ForecastCurrentlyDbModel data);
    }

    interface Presenter{
        void downloadForecastFromApi();
    }
}

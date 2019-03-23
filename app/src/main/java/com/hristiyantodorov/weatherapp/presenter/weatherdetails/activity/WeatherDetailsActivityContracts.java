package com.hristiyantodorov.weatherapp.presenter.weatherdetails.activity;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

public interface WeatherDetailsActivityContracts {

    interface View extends BaseView<Presenter> {

        void showForecast(ForecastCurrentlyDbModel data, String timezone);
    }

    interface Presenter {

        void downloadForecastFromApi(Context context);
    }
}

package com.hristiyantodorov.weatherapp.presenter.weatherdetailsfragment;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import io.reactivex.Single;

public interface WeatherDetailsFragmentContracts {

    interface View extends BaseView<Presenter> {

        void showForecastCurrentlyData(ForecastCurrentlyDbModel data, String hourlySummary, String dailySummary);
    }

    interface Presenter {

        void requestDataFromApi();

        Single<ForecastFullResponse> saveApiDataToDb(ForecastFullResponse fullResponse);

        void clearDisposables();
    }
}
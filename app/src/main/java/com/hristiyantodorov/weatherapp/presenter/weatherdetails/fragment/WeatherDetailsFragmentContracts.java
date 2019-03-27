package com.hristiyantodorov.weatherapp.presenter.weatherdetails.fragment;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import io.reactivex.Single;

public interface WeatherDetailsFragmentContracts {

    interface View extends BaseView<Presenter> {

        void showForecastCurrentlyData(ForecastCurrentlyDbModel data, String hourlySummary, String dailySummary);
    }

    interface Presenter {

        void requestDataFromApi(Context context);

//        void loadDataFromDb();

        Single<ForecastFullResponse> saveApiDataToDb(ForecastFullResponse fullResponse, Context context);

        void clearDisposables();
    }
}
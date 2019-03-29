package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import java.util.List;

import io.reactivex.Single;

public interface ForecastDailyContracts {

    interface View extends BaseView<Presenter> {

        void showForecast(List<ForecastDailyDataDbModel> dailyData);

        void updateActivity(ForecastFullResponse response);
    }

    interface Presenter {

        void subscribe(View view);

        void loadDataFromDb(Context context);

        void updateForecastDailyDataFromApi(Context context);

        Single<ForecastFullResponse> saveForecastApiDataToDb(ForecastFullResponse fullResponse, Context context);

        void presentForecastToView(List<ForecastDailyDataDbModel> dailyData);

        void clearDisposables();
    }
}

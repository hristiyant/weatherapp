package com.hristiyantodorov.weatherapp.presenter.forecastdaily;

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

        void loadDataFromDb();

        void updateForecastDailyDataFromApi();

        Single<ForecastFullResponse> saveForecastApiDataToDb(ForecastFullResponse fullResponse);

        void presentForecastToView(List<ForecastDailyDataDbModel> dailyData);

        void clearDisposables();
    }
}

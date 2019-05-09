package com.hristiyantodorov.weatherapp.presenter.forecasthourly;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import java.util.List;

import io.reactivex.Single;

public interface ForecastHourlyContracts {

    interface View extends BaseView<Presenter> {

        void showForecast(List<ForecastCurrentlyDbModel> hourlyData);

        void updateActivity(ForecastFullResponse response);
    }

    interface Presenter {

        void loadDataFromDb();

        void updateForecastHourlyDataFromApi();

        Single<ForecastFullResponse> saveForecastApiDataToDb(ForecastFullResponse fullResponse);

        void presentForecastToView(List<ForecastCurrentlyDbModel> hourlyData);

        void clearDisposables();
    }
}
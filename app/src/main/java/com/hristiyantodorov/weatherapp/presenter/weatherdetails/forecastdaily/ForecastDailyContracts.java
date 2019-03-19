package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily;

import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastDailyDataResponse;

import java.util.List;

public interface ForecastDailyContracts {

    interface View extends BaseView<Presenter> {

        void showForecastDailyData(List<ForecastDailyDataResponse> result);

        void showEmptyForecast();

    }

    interface Presenter {

        void requestForecastDailyDataFromApi(String latitude, String longitude);

        void saveForecastApiDataToDb(ForecastFullDbModel fullDbModel);

        void presentForecastToView(List<ForecastDailyDataResponse> response);

        void clearResources();

    }

}

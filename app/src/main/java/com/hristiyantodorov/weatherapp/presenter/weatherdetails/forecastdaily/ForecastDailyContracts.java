package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily;

import com.hristiyantodorov.weatherapp.presenter.BaseView;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastDailyDataResponse;

import java.util.List;

public interface ForecastDailyContracts {

    interface View extends BaseView<Presenter> {

        void showForecastDailyData(List<ForecastDailyDataResponse> result);

    }

    interface Presenter {

        void loadForecastDailyData(String latitude, String longitude);

        void clearResources();

    }

}

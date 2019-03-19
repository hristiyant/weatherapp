package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import java.util.List;

public interface ForecastHourlyContracts {

    interface View extends BaseView<Presenter> {

        void showForecast(List<ForecastCurrentlyDbModel> hourlyData);

        void showEmptyForecast();

    }

    interface Presenter {

        void loadDataFromDb();

        void requestForecastHourlyDataFromApi();

        void saveForecastApiDataToDb(ForecastFullDbModel fullDbModel);

        void presentForecastToView(List<ForecastCurrentlyDbModel> hourlyData);

    }
}
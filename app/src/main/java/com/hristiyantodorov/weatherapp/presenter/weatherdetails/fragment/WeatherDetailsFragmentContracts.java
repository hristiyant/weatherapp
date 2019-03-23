package com.hristiyantodorov.weatherapp.presenter.weatherdetails.fragment;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

public interface WeatherDetailsFragmentContracts {

    interface View extends BaseView<Presenter> {

        void showForecastCurrentlyData(ForecastCurrentlyDbModel data, String hourlySummary, String dailySummary);
    }

    interface Presenter {

        void requestDataFromApi();

        void loadDataFromDb();

        void saveApiDataToDb(ForecastFullDbModel fullDbModel);

        void presentForecastToView(ForecastCurrentlyDbModel data, String hourlySummary, String dailySummary);
    }
}
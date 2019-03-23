package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;

import java.util.List;

public interface ForecastDailyContracts {

    interface View extends BaseView<Presenter> {

        void showForecast(List<ForecastDailyDataDbModel> dailyData);

        void updateActivity(ForecastFullResponse response);
    }

    interface Presenter {

        void subscribe(View view);

        void loadDataFromDb(Context context);

        void updateForecastDailyDataFromApi(Context context);

        void saveForecastApiDataToDb(ForecastFullDbModel fullDbModel, Context context);

        void presentForecastToView(List<ForecastDailyDataDbModel> dailyData);
    }
}

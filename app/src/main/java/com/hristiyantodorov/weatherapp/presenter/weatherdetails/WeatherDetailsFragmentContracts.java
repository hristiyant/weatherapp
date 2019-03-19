package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

public interface WeatherDetailsFragmentContracts {

    interface View extends BaseView<Presenter> {

        void showForecastCurrentlyData(ForecastCurrentlyDbModel data);

    }

    interface Presenter {

        void getData();

        void requestDataFromApi();

        void loadDataFromDb();

        void saveApiDataToDb(ForecastFullDbModel fullDbModel);

        void presentForecastToView(ForecastCurrentlyDbModel data);

    }

    /*interface GetForecastDataInteractor {

        interface OnFinishedListener {

            void onFinished(ForecastFullResponse forecastFullResponse);

            void onFailed(Throwable t);

        }

        void getForecastCurrentlyResponse(OnFinishedListener onFinishedListener);

    }*/
}
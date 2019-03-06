package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.presenter.BaseView;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

public interface WeatherDetailsContracts {

    interface View extends BaseView<Presenter> {
        void showForecastCurrentlyData(ForecastFullResponse forecastFullResponse);
        //TODO: show/hide loading, empty, error
    }

    interface Presenter extends BasePresenter {
        void requestForecastCurrentlyFromApi();
    }

    interface GetForecastDataInteractor {

        interface OnFinishedListener {

            void onFinished(ForecastFullResponse forecastFullResponse);

            void onFailed(Throwable t);

        }

        void getForecastCurrentlyResponse(OnFinishedListener onFinishedListener);

    }

}
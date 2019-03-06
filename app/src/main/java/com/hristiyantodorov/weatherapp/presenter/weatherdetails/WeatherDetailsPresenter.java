package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import android.util.Log;

import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

public class WeatherDetailsPresenter implements WeatherDetailsContracts.Presenter,
        WeatherDetailsContracts.GetForecastDataInteractor.OnFinishedListener {

    private static final String TAG  = "WDPresenter";

    private WeatherDetailsContracts.View view;
    private WeatherDetailsContracts.GetForecastDataInteractor getForecastDataInteractor;

    public WeatherDetailsPresenter(WeatherDetailsContracts.View view,
                                   WeatherDetailsContracts.GetForecastDataInteractor getForecastDataInteractor) {
        this.view = view;
        view.setPresenter(this);
        this.getForecastDataInteractor = getForecastDataInteractor;
    }

    @Override
    public void requestForecastCurrentlyFromApi() {
        // TODO: 3/6/2019 showLoading
        getForecastDataInteractor.getForecastCurrentlyResponse(this);
    }

    @Override
    public void onFinished(ForecastFullResponse forecastFullResponse) {
        if(view != null){
            view.showForecastCurrentlyData(forecastFullResponse);
        }
    }

    @Override
    public void onFailed(Throwable t) {
        if(view != null){
            Log.d(TAG, "Failed! ");
        }
    }
}

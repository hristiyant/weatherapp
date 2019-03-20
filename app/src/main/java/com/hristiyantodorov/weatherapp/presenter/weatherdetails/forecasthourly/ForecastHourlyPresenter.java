package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingService;
import com.hristiyantodorov.weatherapp.ui.ExceptionHandlerUtil;

public class ForecastHourlyPresenter implements ForecastHourlyContracts.Presenter, DownloadResponse<WeatherData> {

    private ForecastHourlyContracts.View view;

    public ForecastHourlyPresenter(ForecastHourlyContracts.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadForecastHourlyData() {
        view.showLoader(true);
        new NetworkingService().getWeatherDataHourly(
                this
        );
    }

    @Override
    public void onSuccess(WeatherData object) {
        view.showForecastHourlyData(object.getHourly().getData());
    }

    @Override
    public void onFailure(Exception e) {
        view.showError(e);
        ExceptionHandlerUtil.logStackTrace(e);
    }
}

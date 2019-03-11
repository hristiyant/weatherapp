package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

public class ForecastHourlyPresenter implements ForecastHourlyContracts.Presenter, DownloadResponse<WeatherData> {

    private ForecastHourlyContracts.View view;

    public ForecastHourlyPresenter(ForecastHourlyContracts.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadForecastHourlyData() {
        view.showLoader(true);
        new NetworkingServiceUtil().getWeatherDataHourly(
                this,
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null)
        );
        view.showLoader(false);
    }

    @Override
    public void onSuccess(WeatherData object) {
        view.showForecastHourlyData(object.getHourly().getData());
    }

    @Override
    public void onFailure(Exception e) {
// TODO: 3/1/2019 CURRENTLY NOT BEING USED
    }
}

package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.util.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.util.retrofit.WeatherApiService;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class ForecastHourlyPresenter extends BasePresenter
        implements ForecastHourlyContracts.Presenter {

    private ForecastHourlyContracts.View view;
    private WeatherApiService weatherApiService;

    public ForecastHourlyPresenter(ForecastHourlyContracts.View view) {
        this.view = view;
        view.setPresenter(this);
        weatherApiService = APIClient.getClient().create(WeatherApiService.class);
    }

    @Override
    public void loadForecastHourlyData() {
        view.showLoader(true);

        subscribeSingle(weatherApiService.getForecastHourly(
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null)
        ), new SingleObserver<ForecastFullResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ForecastFullResponse response) {
                // PersistenceDatabase.getAppDatabase().forecastHourlyDao().
            }

            @Override
            public void onError(Throwable e) {

            }
        });
        view.showLoader(false);
    }

   /* @Override
    public void presentForecastToView(List<LocationDbModel> locations) {
        if (locations.isEmpty()) {
            view.showEmptyForecast();
        } else {
            view.showLocations(locations);
        }
    }*/
}
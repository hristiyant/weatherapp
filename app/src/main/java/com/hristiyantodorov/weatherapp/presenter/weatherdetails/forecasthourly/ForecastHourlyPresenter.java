package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import android.content.Context;
import android.util.Log;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastHourlyDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.util.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.util.retrofit.WeatherApiService;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class ForecastHourlyPresenter extends BasePresenter
        implements ForecastHourlyContracts.Presenter {

    private static final String TAG = "FHPresenter";

    private ForecastHourlyContracts.View view;
    private WeatherApiService weatherApiService;

    public ForecastHourlyPresenter(ForecastHourlyContracts.View view) {
        this.view = view;
        view.setPresenter(this);
        weatherApiService = APIClient.getClient().create(WeatherApiService.class);
    }

    @Override
    public void loadDataFromDb() {
        Context context = App.getInstance().getApplicationContext();
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastFullRx(),
                new SingleObserver<ForecastFullDbModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ForecastFullDbModel fullDbModel) {
                        long fullId = fullDbModel.getId();
                        loadHourlyFromDb(fullId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void loadHourlyFromDb(long fullId) {
        Context context = App.getInstance().getApplicationContext();
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastHourlyById(fullId),
                new SingleObserver<ForecastHourlyDbModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ForecastHourlyDbModel forecastHourlyDbModel) {
                        long hourlyId = forecastHourlyDbModel.hourlyId;
                        loadHourlyDataFromDb(hourlyId);
                        Log.d(TAG, "hourlyId: " + hourlyId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void loadHourlyDataFromDb(long hourlyId) {
        Context context = App.getInstance().getApplicationContext();
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastCurrentlyByHourlyId(hourlyId),
                new SingleObserver<List<ForecastCurrentlyDbModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ForecastCurrentlyDbModel> forecastCurrentlyDbModels) {
                        Log.d(TAG, "currently list size: " + forecastCurrentlyDbModels.size());
                        presentForecastToView(forecastCurrentlyDbModels);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    @Override
    public void requestForecastHourlyDataFromApi() {
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

    @Override
    public void saveForecastApiDataToDb(ForecastFullDbModel fullDbModel) {
        // TODO: 3/19/2019
    }

    @Override
    public void presentForecastToView(List<ForecastCurrentlyDbModel> currentlyDbModels) {
        if (currentlyDbModels.isEmpty()) {
            view.showEmptyForecast();
        } else {
            view.showForecast(currentlyDbModels);
        }
    }
}
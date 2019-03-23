package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import android.content.Context;
import android.util.Log;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastHourlyDbModel;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.retrofit.WeatherApiService;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.ForecastResponseToForecastDbModelConverterUtil;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    public void subscribe(ForecastHourlyContracts.View view) {
        this.view = view;
    }

    @Override
    public void loadDataFromDb(Context context) {
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastFullRx(),
                new SingleObserver<ForecastFullDbModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(ForecastFullDbModel fullDbModel) {
                        long fullId = fullDbModel.getId();
                        loadHourlyFromDb(fullId, context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }
                });
    }

    private void loadHourlyFromDb(long fullId, Context context) {
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastHourlyById(fullId),
                new SingleObserver<ForecastHourlyDbModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(ForecastHourlyDbModel forecastHourlyDbModel) {
                        long hourlyId = forecastHourlyDbModel.hourlyId;
                        loadHourlyDataFromDb(hourlyId, context);
                        Log.d(TAG, "hourlyId: " + hourlyId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }
                });
    }

    private void loadHourlyDataFromDb(long hourlyId, Context context) {
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastHourlyDataByHourlyId(hourlyId),
                new SingleObserver<List<ForecastCurrentlyDbModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(List<ForecastCurrentlyDbModel> forecastCurrentlyDbModels) {
                        Log.d(TAG, "currently list size: " + forecastCurrentlyDbModels.size());
                        presentForecastToView(forecastCurrentlyDbModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }
                });
    }

    @Override
    public void updateForecastHourlyDataFromApi(Context context) {
        subscribeSingle(weatherApiService.getForecastFullResponse(
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null),
                SharedPrefUtil.read("shared_pref_api_content_lang_key", "en")
        ), new SingleObserver<ForecastFullResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                // TODO: 3/18/2019 CURRENTLY NOT BEING USED
            }

            @Override
            public void onSuccess(ForecastFullResponse response) {
                saveForecastApiDataToDb(ForecastResponseToForecastDbModelConverterUtil
                        .convertResponseToDbModel(response), context);
                view.updateActivity(response);
                presentForecastToView(ForecastResponseToForecastDbModelConverterUtil
                        .convertHourlyDataResponseListToDbModelList(response
                                .getHourly()
                                .getData()));
            }

            @Override
            public void onError(Throwable e) {
                // TODO: 3/18/2019 CURRENTLY NOT BEING USED
            }
        });
    }

    @Override
    public void saveForecastApiDataToDb(ForecastFullDbModel fullDbModel, Context context) {
        Completable.fromRunnable(
                () -> PersistenceDatabase
                        .getAppDatabase(context)
                        .forecastFullDao().updateDb(fullDbModel)
        ).subscribeOn(Schedulers.io())
                .subscribe();
        Log.d(TAG, "saveForecastApiDataToDb");
    }

    @Override
    public void presentForecastToView(List<ForecastCurrentlyDbModel> hourlyData) {
        if (hourlyData.isEmpty()) {
            view.showEmptyScreen(true);
        } else {
            view.showForecast(hourlyData);
            Log.d(TAG, "presentForecastToView: PRESENTING HOURLY FORECAST TO VIEW");
        }
    }
}
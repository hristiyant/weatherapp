package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import android.content.Context;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.ForecastResponseToForecastDbModelConverterUtil;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.util.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.util.retrofit.WeatherApiService;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherDetailsActivityPresenter extends BasePresenter
        implements WeatherDetailsActivityContracts.Presenter {

    private static final String TAG = "WDAPresenter";

    private WeatherDetailsActivityContracts.View view;
    private WeatherApiService service;

    public WeatherDetailsActivityPresenter(WeatherDetailsActivityContracts.View view) {
        this.view = view;
        view.setPresenter(this);
        this.service = APIClient.getClient().create(WeatherApiService.class);
    }

    @Override
    public void downloadForecastFromApi() {
        view.showLoader(true);
        subscribeSingle(
                service.getForecastFullResponse(
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null)
                ), new SingleObserver<ForecastFullResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ForecastFullResponse forecastFullResponse) {
                        presentForecastToView(ForecastResponseToForecastDbModelConverterUtil
                                .convertCurrentlyResponseToDbModel(forecastFullResponse.getCurrently()));
                        saveApiDataToDb(
                                ForecastResponseToForecastDbModelConverterUtil
                                        .convertResponseToDbModel(forecastFullResponse)
                        );
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }
        );
    }

    public void saveApiDataToDb(ForecastFullDbModel fullDbModel) {
        Context context = App.getInstance().getApplicationContext();
        Completable.fromRunnable(
                () -> PersistenceDatabase
                        .getAppDatabase(context)
                        .forecastFullDao().updateDb(fullDbModel)
        ).subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void presentForecastToView(ForecastCurrentlyDbModel data) {
        if (data == null) {
            view.showEmptyScreen(true);
        } else {
            view.showForecast(data);
        }
    }
}
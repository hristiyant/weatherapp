package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import android.content.Context;

import com.hristiyantodorov.weatherapp.App;
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

public class WeatherDetailsPresenter extends BasePresenter
        implements WeatherDetailsContracts.Presenter {

    private static final String TAG = "WDPresenter";

    private WeatherDetailsContracts.View view;
    private WeatherDetailsContracts.GetForecastDataInteractor getForecastDataInteractor;
    private WeatherApiService service;

    public WeatherDetailsPresenter(WeatherDetailsContracts.View view,
                                   WeatherDetailsContracts.GetForecastDataInteractor getForecastDataInteractor) {
        this.view = view;
        view.setPresenter(this);
        this.getForecastDataInteractor = getForecastDataInteractor;
        this.service = APIClient.getClient().create(WeatherApiService.class);
    }

    @Override
    public void requestForecastCurrentlyFromApi() {
        view.showLoader(true);

        //getForecastDataInteractor.getForecastCurrentlyResponse(this);
        subscribeSingle(
                service.getForecastCurrently(
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null)
                ), new SingleObserver<ForecastFullResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ForecastFullResponse forecastFullResponse) {
                        view.showForecastCurrentlyData(forecastFullResponse);
                        saveForecastApiDataToDb(
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

    @Override
    public void saveForecastApiDataToDb(ForecastFullDbModel fullDbModel) {

        //TODO: SHOW PROGRESSBAR
        Context context = App.getInstance().getApplicationContext();
        Completable.fromRunnable(
                () -> PersistenceDatabase
                        .getAppDatabase(context)
                        .forecastFullDao().updateDb(fullDbModel)
        ).subscribeOn(Schedulers.io())
                .subscribe();
        //TODO: DISPOSE

    }



}
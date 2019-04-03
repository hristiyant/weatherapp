package com.hristiyantodorov.weatherapp.presenter.weatherdetails.activity;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.response.ForecastCurrentlyResponse;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.service.ForecastApiService;
import com.hristiyantodorov.weatherapp.service.ForecastDbService;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.ForecastResponseToForecastDbModelConverterUtil;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class WeatherDetailsActivityPresenter extends BasePresenter
        implements WeatherDetailsActivityContracts.Presenter {

    private static final String TAG = "WDAPresenter";

    @Inject
    ForecastApiService forecastApiService;
    @Inject
    ForecastDbService forecastDbService;

    private String timezone;
    private WeatherDetailsActivityContracts.View view;

    public WeatherDetailsActivityPresenter(WeatherDetailsActivityContracts.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void downloadForecastFromApi(Context context) {
        view.showLoader(true);
        subscribeSingle(
                forecastApiService.getForecastFullResponse(
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null),
                        SharedPrefUtil.read(Constants.LANGUAGE_KEY, "en")
                )
                        .flatMap(fullResponse -> saveForecastApiDataToDb(fullResponse, context)),
                fullResponse -> {
                    timezone = fullResponse.getTimezone();
                    presentForecastToView(fullResponse.getCurrently());
                },
                throwable -> view.showError(throwable)
        );
    }

    private Single<ForecastFullResponse> saveForecastApiDataToDb(ForecastFullResponse fullResponse, Context context) {
        return Completable.fromRunnable(() -> forecastDbService
                .updateDb(ForecastResponseToForecastDbModelConverterUtil.convertResponseToDbModel(fullResponse)))
                .toSingleDefault(fullResponse);
    }

    private void presentForecastToView(ForecastCurrentlyResponse data) {
        if (data == null) {
            view.showEmptyScreen(true);
        } else {
            view.showForecast(data, timezone);
        }
    }

    public String getTimestamp() {
        return DateFormat
                .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
                .format(new java.util.Date());
    }

    @Override
    public void clearDisposables() {
        super.clearDisposables();
    }

    @Override
    protected void inject() {
        provideAppComponent().inject(this);
    }
}
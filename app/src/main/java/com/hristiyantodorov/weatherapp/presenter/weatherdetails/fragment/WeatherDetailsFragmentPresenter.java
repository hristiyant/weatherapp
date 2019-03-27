package com.hristiyantodorov.weatherapp.presenter.weatherdetails.fragment;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.retrofit.WeatherApiService;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.ForecastResponseToForecastDbModelConverterUtil;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import io.reactivex.Completable;
import io.reactivex.Single;

public class WeatherDetailsFragmentPresenter extends BasePresenter
        implements WeatherDetailsFragmentContracts.Presenter {

    private static final String TAG = "WDPresenter";

    private WeatherDetailsFragmentContracts.View view;
    private WeatherApiService service;
    private String hourlySummary;
    private String dailySummary;

    public WeatherDetailsFragmentPresenter(WeatherDetailsFragmentContracts.View view) {
        this.view = view;
        view.setPresenter(this);
        this.service = APIClient.getClient().create(WeatherApiService.class);
    }

    @Override
    public void requestDataFromApi(Context context) {
        view.showLoader(true);
        subscribeSingle(
                service.getForecastFullResponse(
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null),
                        SharedPrefUtil.read(Constants.LANGUAGE_KEY, "en")
                )
                        .flatMap(fullResponse -> saveApiDataToDb(fullResponse, context)),
                fullResponse -> {
                    hourlySummary = fullResponse.getHourly().getSummary();
                    dailySummary = fullResponse.getDaily().getSummary();
                    presentForecastToView(fullResponse, hourlySummary, dailySummary);
                },
                throwable -> view.showError(throwable)
        );
    }

    @Override
    public Single<ForecastFullResponse> saveApiDataToDb(ForecastFullResponse fullResponse, Context context) {
        return Completable.fromRunnable(() -> PersistenceDatabase
                .getAppDatabase(context)
                .forecastFullDao().updateDb(ForecastResponseToForecastDbModelConverterUtil.convertResponseToDbModel(fullResponse))
        ).toSingleDefault(fullResponse);
    }

    private void presentForecastToView(ForecastFullResponse data, String hourlySummary, String dailySummary) {
        if (data == null) {
            view.showEmptyScreen(true);
        } else {
            view.showForecastCurrentlyData(ForecastResponseToForecastDbModelConverterUtil
                    .convertCurrentlyResponseToDbModel(data.getCurrently()), hourlySummary, dailySummary);
        }
    }

    @Override
    public void clearDisposables() {
        super.clearDisposables();
    }
}
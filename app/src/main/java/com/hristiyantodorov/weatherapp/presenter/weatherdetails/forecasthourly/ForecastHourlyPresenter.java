package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
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
import io.reactivex.Single;

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
        subscribeSingle(
                PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastFullRx()
                        .flatMap(fullDbModel -> PersistenceDatabase.getAppDatabase(context)
                                .forecastFullDao().getForecastHourlyById(fullDbModel.getId()))
                        .flatMap(forecastHourlyDbModel -> PersistenceDatabase.getAppDatabase(context)
                                .forecastFullDao().getForecastHourlyDataByHourlyId(forecastHourlyDbModel.getHourlyId())),
                this::presentForecastToView,
                throwable -> view.showError(throwable)
        );
    }

    @Override
    public void updateForecastHourlyDataFromApi(Context context) {
        subscribeSingle(
                weatherApiService.getForecastFullResponse(
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null),
                        SharedPrefUtil.read(Constants.LANGUAGE_KEY, "en")
                ).flatMap(fullResponse -> saveForecastApiDataToDb(fullResponse, context)),
                fullResponse -> {
                    view.updateActivity(fullResponse);
                    presentForecastToView(ForecastResponseToForecastDbModelConverterUtil
                            .convertHourlyDataResponseListToDbModelList(fullResponse
                                    .getHourly()
                                    .getData()));
                },
                throwable -> view.showError(throwable)
        );
    }

    @Override
    public Single<ForecastFullResponse> saveForecastApiDataToDb(ForecastFullResponse fullResponse, Context context) {
        return Completable.fromRunnable(() -> PersistenceDatabase
                .getAppDatabase(context)
                .forecastFullDao()
                .updateDb(ForecastResponseToForecastDbModelConverterUtil.convertResponseToDbModel(fullResponse)))
                .toSingleDefault(fullResponse);
    }

    @Override
    public void presentForecastToView(List<ForecastCurrentlyDbModel> hourlyData) {
        if (hourlyData.isEmpty()) {
            view.showEmptyScreen(true);
        } else {
            view.showForecast(hourlyData);
        }
    }

    @Override
    public void clearDisposables() {
        super.clearDisposables();
    }
}
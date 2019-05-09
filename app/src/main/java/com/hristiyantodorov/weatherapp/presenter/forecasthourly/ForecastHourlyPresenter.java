package com.hristiyantodorov.weatherapp.presenter.forecasthourly;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.service.ForecastApiService;
import com.hristiyantodorov.weatherapp.service.ForecastDbService;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.ForecastResponseToForecastDbModelConverterUtil;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ForecastHourlyPresenter extends BasePresenter
        implements ForecastHourlyContracts.Presenter {

    private static final String TAG = "FHPresenter";

    @Inject
    ForecastApiService forecastApiService;
    @Inject
    ForecastDbService forecastDbService;

    private ForecastHourlyContracts.View view;

    public ForecastHourlyPresenter(ForecastHourlyContracts.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadDataFromDb() {
        subscribeSingle(
                forecastDbService.getForecastFullRx()
                        .flatMap(fullDbModel -> forecastDbService.
                                getForecastHourlyById(fullDbModel.getId()))
                        .flatMap(forecastHourlyDbModel -> forecastDbService
                                .getForecastHourlyDataByHourlyId(forecastHourlyDbModel.getHourlyId())),
                this::presentForecastToView,
                throwable -> view.showError(throwable)
        );
    }

    @Override
    public void updateForecastHourlyDataFromApi() {
        subscribeSingle(
                forecastApiService.getForecastFullResponse(
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null),
                        SharedPrefUtil.read(Constants.LANGUAGE_KEY, "en")
                ).flatMap(this::saveForecastApiDataToDb),
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
    public Single<ForecastFullResponse> saveForecastApiDataToDb(ForecastFullResponse fullResponse) {
        return Completable.fromRunnable(() -> forecastDbService
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
    protected void inject() {
        provideAppComponent().inject(this);
    }

    @Override
    public void clearDisposables() {
        super.clearDisposables();
    }
}
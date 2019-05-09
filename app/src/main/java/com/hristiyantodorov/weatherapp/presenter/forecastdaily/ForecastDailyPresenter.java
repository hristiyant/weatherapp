package com.hristiyantodorov.weatherapp.presenter.forecastdaily;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;
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

public class ForecastDailyPresenter extends BasePresenter
        implements ForecastDailyContracts.Presenter {

    private static final String TAG = "FDPresenter";

    @Inject
    ForecastApiService forecastApiService;
    @Inject
    ForecastDbService forecastDbService;

    private ForecastDailyContracts.View view;

    public ForecastDailyPresenter(ForecastDailyContracts.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadDataFromDb() {
        subscribeSingle(
                forecastDbService.getForecastFullRx()
                        .flatMap(fullDbModel -> forecastDbService
                                .getForecastDailyById(fullDbModel.getId()))
                        .flatMap(forecastDailyDbModel -> forecastDbService
                                .getForecastDailyDataByDailyId(forecastDailyDbModel.getDailyId())),
                this::presentForecastToView,
                throwable -> view.showError(throwable)
        );
    }

    @Override
    public void updateForecastDailyDataFromApi() {
        subscribeSingle(
                forecastApiService.getForecastFullResponse(
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null),
                        SharedPrefUtil.read(Constants.LANGUAGE_KEY, "en")
                )
                        .flatMap(this::saveForecastApiDataToDb),
                fullResponse -> {
                    view.updateActivity(fullResponse);
                    presentForecastToView(ForecastResponseToForecastDbModelConverterUtil
                            .convertDailyDataResponseListToDbModelList(fullResponse
                                    .getDaily()
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
    public void presentForecastToView(List<ForecastDailyDataDbModel> dailyData) {
        if (dailyData.isEmpty()) {
            view.showEmptyScreen(true);
        } else {
            view.showForecast(dailyData);
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

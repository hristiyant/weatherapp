package com.hristiyantodorov.weatherapp.presenter.weatherdetails.activity;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.retrofit.WeatherApiService;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.ForecastResponseToForecastDbModelConverterUtil;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherDetailsActivityPresenter extends BasePresenter
        implements WeatherDetailsActivityContracts.Presenter {

    private static final String TAG = "WDAPresenter";

    private WeatherDetailsActivityContracts.View view;
    private WeatherApiService service;
    private String timezone;

    public WeatherDetailsActivityPresenter(WeatherDetailsActivityContracts.View view) {
        this.view = view;
        view.setPresenter(this);
        this.service = APIClient.getClient().create(WeatherApiService.class);
    }

    @Override
    public void downloadForecastFromApi(Context context) {
        view.showLoader(true);
        subscribeSingle(
                service.getForecastCurrently(
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null),
                        SharedPrefUtil.read("shared_pred_api_content_lang_key", "en")
                ), new SingleObserver<ForecastFullResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 23.3.2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(ForecastFullResponse forecastFullResponse) {
                        timezone = forecastFullResponse.getTimezone();
                        ForecastCurrentlyDbModel currentlyDbModel =
                                ForecastResponseToForecastDbModelConverterUtil
                                        .convertCurrentlyResponseToDbModel(forecastFullResponse.getCurrently());
                        presentForecastToView(currentlyDbModel);
                        /*saveApiDataToDb(
                                ForecastResponseToForecastDbModelConverterUtil
                                        .convertResponseToDbModel(forecastFullResponse),
                                context
                        );*/
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 23.3.2019 CURRENTLY NOT BEING USED
                    }
                }
        );
    }

    public void saveApiDataToDb(ForecastFullDbModel fullDbModel, Context context) {
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
            view.showForecast(data, timezone);
        }
    }
}
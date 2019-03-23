package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily;

import android.content.Context;
import android.util.Log;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.ForecastResponseToForecastDbModelConverterUtil;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.retrofit.WeatherApiService;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForecastDailyPresenter extends BasePresenter
        implements ForecastDailyContracts.Presenter {

    private static final String TAG = "FDPresenter";

    private ForecastDailyContracts.View view;
    private WeatherApiService weatherApiService;
//    private CompositeDisposable disposable = new CompositeDisposable();

    public ForecastDailyPresenter(ForecastDailyContracts.View view) {
        this.view = view;
        view.setPresenter(this);
        this.weatherApiService = APIClient.getClient().create(WeatherApiService.class);
    }

    @Override
    public void subscribe(ForecastDailyContracts.View view) {
        this.view = view;
    }

    @Override
    public void loadDataFromDb(Context context) {
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastFullRx(),
                new SingleObserver<ForecastFullDbModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ForecastFullDbModel fullDbModel) {
                        loadDailyFromDb(fullDbModel.getId(), context);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void loadDailyFromDb(Long fullId, Context context) {
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastDailyById(fullId),
                new SingleObserver<ForecastDailyDbModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ForecastDailyDbModel forecastDailyDbModel) {
                        long dailyId = forecastDailyDbModel.dailyId;
                        loadDailyDataFromDb(dailyId, context);
                        Log.d(TAG, "dailyId: " + dailyId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void loadDailyDataFromDb(long dailyId, Context context) {
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastDailyDataByDailyId(dailyId),
                new SingleObserver<List<ForecastDailyDataDbModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ForecastDailyDataDbModel> forecastDailyDbModels) {
                        Log.d(TAG, "currently list size: " + forecastDailyDbModels.size());
                        presentForecastToView(forecastDailyDbModels);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void updateForecastDailyDataFromApi(Context context) {

        subscribeSingle(weatherApiService.getForecastFullResponse(
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null),
                SharedPrefUtil.read("shared_pred_api_content_lang_key", "en")
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
                        .convertDailyDataResponseListToDbModelList(response
                                .getDaily()
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
    public void presentForecastToView(List<ForecastDailyDataDbModel> dailyData) {
        if (dailyData.isEmpty()) {
            view.showEmptyScreen(true);
        } else {
            view.showForecast(dailyData);
            Log.d(TAG, "presentForecastToView: PRESENTING HOURLY FORECAST TO VIEW");
        }
    }
}

package com.hristiyantodorov.weatherapp.presenter.weatherdetails.fragment;

import android.content.Context;
import android.util.Log;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastHourlyDbModel;
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

public class WeatherDetailsFragmentPresenter extends BasePresenter
        implements WeatherDetailsFragmentContracts.Presenter {

    private static final String TAG = "WDPresenter";

    private WeatherDetailsFragmentContracts.View view;
    private WeatherApiService service;
    private Context context;
    private String hourlySummary;
    private String dailySummary;

    public WeatherDetailsFragmentPresenter(WeatherDetailsFragmentContracts.View view) {
        this.view = view;
        view.setPresenter(this);
        this.service = APIClient.getClient().create(WeatherApiService.class);
    }

    @Override
    public void requestDataFromApi() {
        view.showLoader(true);

        subscribeSingle(
                service.getForecastFullResponse(
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                        SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null),
                        SharedPrefUtil.read("shared_pref_api_content_lang_key", "en")
                ), new SingleObserver<ForecastFullResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(ForecastFullResponse forecastFullResponse) {
                        dailySummary = forecastFullResponse.getDaily().getSummary();
                        hourlySummary = forecastFullResponse.getHourly().getSummary();
                        presentForecastToView(ForecastResponseToForecastDbModelConverterUtil
                                .convertCurrentlyResponseToDbModel(forecastFullResponse.getCurrently()), hourlySummary, dailySummary);
                        saveApiDataToDb(
                                ForecastResponseToForecastDbModelConverterUtil
                                        .convertResponseToDbModel(forecastFullResponse)
                        );
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }
                }
        );
    }

    @Override
    public void loadDataFromDb() {
        context = App.getInstance().getApplicationContext();
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastFullRx(),
                new SingleObserver<ForecastFullDbModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(ForecastFullDbModel fullDbModel) {
                        long id = fullDbModel.getId();
                        getDailySummary(id);
                        getHourlySummary(id);
                        loadCurrentlyDataFromDb(id);

                        Log.d(TAG, "id: " + id);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }
                });
    }

    @Override
    public void saveApiDataToDb(ForecastFullDbModel fullDbModel) {

        //TODO: SHOW PROGRESSBAR
        context = App.getInstance().getApplicationContext();
        Completable.fromRunnable(
                () -> PersistenceDatabase
                        .getAppDatabase(context)
                        .forecastFullDao().updateDb(fullDbModel)
        ).subscribeOn(Schedulers.io())
                .subscribe();
        //TODO: DISPOSE

    }

    private void loadCurrentlyDataFromDb(long id) {
        context = App.getInstance().getApplicationContext();
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastCurrentlyByFullId(id),
                new SingleObserver<List<ForecastCurrentlyDbModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(List<ForecastCurrentlyDbModel> currentlyDbModels) {
                        if (currentlyDbModels.get(0) == null) {
                            requestDataFromApi();
                        }
                        presentForecastToView(currentlyDbModels.get(0), hourlySummary, dailySummary);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }
                });
    }

    private void getHourlySummary(long fullId) {
        context = App.getInstance().getApplicationContext();
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastHourlyById(fullId),
                new SingleObserver<ForecastHourlyDbModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(ForecastHourlyDbModel hourlyDbModel) {
                        hourlySummary = hourlyDbModel.getSummary();
                        Log.d(TAG, "Daily summary: " + hourlySummary);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }
                });
    }

    private void getDailySummary(long fullId) {
        context = App.getInstance().getApplicationContext();
        subscribeSingle(PersistenceDatabase.getAppDatabase(context).forecastFullDao().getForecastDailyById(fullId),
                new SingleObserver<ForecastDailyDbModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(ForecastDailyDbModel dailyDbModel) {
                        dailySummary = dailyDbModel.getSummary();
                        Log.d(TAG, "Daily summary: " + dailySummary);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 3/18/2019 CURRENTLY NOT BEING USED
                    }
                });
    }

    @Override
    public void presentForecastToView(ForecastCurrentlyDbModel data, String hourlySummary, String dailySummary) {
        if (data == null) {
            view.showEmptyScreen(true);
        } else {
            view.showForecastCurrentlyData(data, hourlySummary, dailySummary);
        }
    }
}
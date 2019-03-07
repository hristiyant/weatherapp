package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import android.util.Log;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.util.AppExecutorUtil;
import com.hristiyantodorov.weatherapp.util.ForecastResponseToForecastDbModelConverterUtil;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

public class WeatherDetailsPresenter implements WeatherDetailsContracts.Presenter,
        WeatherDetailsContracts.GetForecastDataInteractor.OnFinishedListener {

    private static final String TAG = "WDPresenter";

    private WeatherDetailsContracts.View view;
    private WeatherDetailsContracts.GetForecastDataInteractor getForecastDataInteractor;

    public WeatherDetailsPresenter(WeatherDetailsContracts.View view,
                                   WeatherDetailsContracts.GetForecastDataInteractor getForecastDataInteractor) {
        this.view = view;
        view.setPresenter(this);
        this.getForecastDataInteractor = getForecastDataInteractor;
    }

    @Override
    public void requestForecastCurrentlyFromApi() {
        // TODO: 3/6/2019 showLoading
        getForecastDataInteractor.getForecastCurrentlyResponse(this);
    }

    @Override
    public void saveForecastApiDataToDb(ForecastFullDbModel fullDbModel, ForecastCurrentlyDbModel currentlyDbModel) {
        AppExecutorUtil.getInstance().execute(() -> PersistenceDatabase.getAppDatabase(App.getInstance().getApplicationContext())
                .forecastFullDao()
                .insert(fullDbModel));
    }

    @Override
    public void onFinished(ForecastFullResponse forecastFullResponse) {
        if (view != null) {
            view.showForecastCurrentlyData(forecastFullResponse);
            saveForecastApiDataToDb(
                    ForecastResponseToForecastDbModelConverterUtil.convertResponseToDbModel(forecastFullResponse),
                    ForecastResponseToForecastDbModelConverterUtil.convertCurrentelyResponseToDbModel(forecastFullResponse.getCurrently())
            );
        }
    }

    @Override
    public void onFailed(Throwable t) {
        if (view != null) {
            Log.d(TAG, "Failed! ");
        }
    }
}
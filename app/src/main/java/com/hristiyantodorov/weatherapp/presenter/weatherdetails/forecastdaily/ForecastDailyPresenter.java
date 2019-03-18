package com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily;

import com.hristiyantodorov.weatherapp.util.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.util.retrofit.WeatherApiService;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastDailyDataResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForecastDailyPresenter implements ForecastDailyContracts.Presenter {

    private ForecastDailyContracts.View view;
    private WeatherApiService weatherApiService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ForecastDailyPresenter(ForecastDailyContracts.View view) {
        this.view = view;
        view.setPresenter(this);
        this.weatherApiService = APIClient.getClient().create(WeatherApiService.class);
    }

    @Override
    public void loadForecastDailyData(String latitude, String longitude) {
        disposable.add(
                weatherApiService.getForecastDaily(latitude, longitude)
                        .doOnSubscribe(disposable -> view.showLoader(true))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnEvent((forecastFullResponse, throwable) -> view.showLoader(false))
                        .map(forecastFullResponse -> forecastFullResponse.getDaily().getData())
                        .subscribeWith(new DisposableSingleObserver<List<ForecastDailyDataResponse>>() {
                            @Override
                            public void onSuccess(List<ForecastDailyDataResponse> result) {
                                view.showForecastDailyData(result);
                            }

                            @Override
                            public void onError(Throwable e) {
                                //TODO: CURRENTLY NOT BEING USED
                            }
                        }));
    }

    @Override
    public void clearResources() {
        disposable.dispose();
    }

    public void selectDailyItem(ForecastDailyDataResponse item){

    }
}

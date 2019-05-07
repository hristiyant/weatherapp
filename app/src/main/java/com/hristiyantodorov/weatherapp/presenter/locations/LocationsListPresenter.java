package com.hristiyantodorov.weatherapp.presenter.locations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.service.ForecastApiService;
import com.hristiyantodorov.weatherapp.service.LocationsDbService;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocationsListPresenter extends BasePresenter
        implements LocationsListContracts.Presenter {

    private static final String TAG = "LLPresenter";

    @Inject
    ForecastApiService weatherApiService;
    @Inject
    LocationsDbService locationsDbService;
    private LocationsListContracts.View view;
    private List<LocationDbModel> locationsTemp;

    public LocationsListPresenter(LocationsListContracts.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void loadDbData() {
        addToCompositeDisposable(subscribeSingle(
                locationsDbService.getAllLocationsList()
                        .flatMap(locationDbModels -> {
                            locationsTemp = locationDbModels;
                            return locationsDbService.checkForNullIcons();
                        }),
                integer -> {
                    if (integer == 0) {
                        presentLocationsToView(locationsTemp);
                        Log.d(TAG, "No null icons");
                    } else {
                        fillDbFromApi(locationsTemp);
                        Log.d(TAG, "Found null icons");
                    }
                },
                throwable -> view.showError(throwable)));
    }

    @SuppressLint("CheckResult")
    public void fillDbFromApi(List<LocationDbModel> locationDbModels) {
        addToCompositeDisposable(locationsDbService.getAllLocationsList()
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(locationDbModel ->
                        weatherApiService.getForecastCurrently(
                                String.valueOf(locationDbModel.getLatitude()),
                                String.valueOf(locationDbModel.getLongitude()),
                                SharedPrefUtil.read(Constants.LANGUAGE_KEY, "en")
                        ))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((forecastFullResponses, throwable) -> {
                    for (int i = 0; i < forecastFullResponses.size(); i++) {
                        locationDbModels.get(i).setIcon(forecastFullResponses.get(i).getCurrently().getIcon());
                        locationDbModels.get(i).setTemperature(forecastFullResponses.get(i).getCurrently().getTemperature());
                        updateLocationDbInfo(locationDbModels.get(i));
                    }
                    presentLocationsToView(locationDbModels);
                }));
    }

    @Override
    public void updateLocationDbInfo(LocationDbModel locationDbModel) {
        subscribeCompletable(Completable.fromAction(() -> locationsDbService.update(locationDbModel)));
    }

    @Override
    public void filterLocations(String pattern, Context context) {
        addToCompositeDisposable(Observable.create((ObservableOnSubscribe<List<LocationDbModel>>) emitter -> {
            List<LocationDbModel> locations = locationsDbService.getFilteredLocationsList(pattern);
            emitter.onNext(locations);
            emitter.onComplete();
        })
                .doOnSubscribe(disposable -> view.showLoader(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> view.showLoader(false))
                .subscribe(this::presentLocationsToView,
                        error -> view.showError(error)));
    }

    private void presentLocationsToView(List<LocationDbModel> locations) {
        Log.d(TAG, "Presenting locations to view");
        if (locations.isEmpty()) {
            view.showEmptyScreen(true);
            Log.d(TAG, "Showing empty screen");
        } else {
            view.showLocations(locations);
        }
    }

    @Override
    public void selectLocation(String lat, String lon, Context context) {
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LAT, lat);
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LON, lon);
        view.openWeatherDetailsActivity();
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

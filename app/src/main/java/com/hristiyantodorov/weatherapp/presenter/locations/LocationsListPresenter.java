package com.hristiyantodorov.weatherapp.presenter.locations;

import android.annotation.SuppressLint;
import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.networking.services.LocationsDbService;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.retrofit.WeatherApiService;
import com.hristiyantodorov.weatherapp.util.DisposableManagerUtil;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LocationsListPresenter extends BasePresenter
        implements LocationsListContracts.Presenter {

    private static final String TAG = "LLPresenter";

    private LocationsListContracts.View view;
    //private CompositeDisposable disposable = new CompositeDisposable();
    private WeatherApiService weatherApiService;
    private LocationsDbService locationsDbService;
    public static final List<LocationDbModel> defaultLocationsList = Arrays.asList(
            new LocationDbModel("Tokyo", 35.652832, 139.839478),
            new LocationDbModel("New York", 40.730610, -73.935242),
            new LocationDbModel("Paris", 48.864716, 2.349014),
            new LocationDbModel("London", 51.509865, -0.118092),
            new LocationDbModel("Sydney", -33.865143, 151.209900)
    );

    public LocationsListPresenter(LocationsListContracts.View view, Context context) {
        this.view = view;
        this.view.setPresenter(this);
        weatherApiService = APIClient.getClient().create(WeatherApiService.class);
        locationsDbService = LocationsDbService.getInstance(context);
    }

    @Override
    public void loadDbData() {
        subscribeSingle(locationsDbService.getAllLocationsList(),
                new SingleObserver<List<LocationDbModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 20.3.2019 CURRENTLY NOT BEING USED
                    }

                    @SuppressLint("CheckResult")
                    @Override
                    public void onSuccess(List<LocationDbModel> locationDbModels) {
                        if (locationDbModels.isEmpty()) {
                            populateDatabaseWithDefaultList();
                            //check if ther are null icons
                        } else {
                            checkForNullIcons(locationDbModels);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 20.3.2019 CURRENTLY NOT BEING USED
                    }
                });
    }

    private void checkForNullIcons(List<LocationDbModel> locationDbModels) {
        subscribeSingle(locationsDbService.checkForNullIcons(locationDbModels),
                new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO: 20.3.2019 CURRENTLY NOT BEING USED
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        if (integer == 0) {
                            presentLocationsToView(locationDbModels);
                        } else {
                            fillDbFromApi(locationDbModels);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 20.3.2019 CURRENTLY NOT BEING USED
                    }
                });
    }

    private void populateDatabaseWithDefaultList() {
        Completable.fromAction(() -> locationsDbService.populateDatabaseWithDefaultList())
                .doOnComplete(this::loadDbData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @SuppressLint("CheckResult")
    public void fillDbFromApi(List<LocationDbModel> locationDbModels) {
        locationsDbService.getAllLocationsList()
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(locationDbModel ->
                        weatherApiService.getForecastCurrently(
                                String.valueOf(locationDbModel.getLatitude()),
                                String.valueOf(locationDbModel.getLongitude()),
                                SharedPrefUtil.read("shared_pred_api_content_lang_key", "en")
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
                });
    }

//    public void getApiData(LocationDbModel location) {
//        subscribeSingle(weatherApiService.getForecastCurrently(
//                String.valueOf(location.getLatitude()),
//                String.valueOf(location.getLongitude())
//        ), new SingleObserver<ForecastFullResponse>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                // TODO: 20.3.2019 CURRENTLY NOT BEING USED
//            }
//
//            @Override
//            public void onSuccess(ForecastFullResponse forecastFullResponse) {
//                location.setIcon(forecastFullResponse.getCurrently().getIcon());
//                updateLocationDbInfo(location);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//        });
//    }

    /*public void downloadApiDataForDbModels(Context context) {
        subscribeSingle(service.getAllLocationsList(), new SingleObserver<List<LocationDbModel>>() {
            @Override
            public void onSubscribe(Disposable d) {
                //TODO: CURRENTLY NOT BEING USED
            }

            @Override
            public void onSuccess(List<LocationDbModel> locationDbModels) {
                //TODO: CHECK IF RETURNED VALUE IS NULL AND SHOW ERROR IF SO
                if (!locationDbModels.isEmpty()) {
                    view.getBasicForecastInfo(locationDbModels);
                } else {
                    presentLocationsToView(locationDbModels);
                }
            }

            @Override
            public void onError(Throwable e) {
                //TODO: HANDLE ERROR
            }
        });
    }

    @Override
    public void getBasicForecastInfo(LocationDbModel location) {
        subscribeSingle(weatherApiService.getForecastCurrently(
                String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())
        ), new SingleObserver<ForecastFullResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ForecastFullResponse forecastFullResponse) {
                location.setTemperature(forecastFullResponse.getCurrently().getTemperature());
                location.setIcon(forecastFullResponse.getCurrently().getIcon());
                view.updateApiInfo(location);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }*/

    @Override
    public void updateLocationDbInfo(LocationDbModel locationDbModel) {
        Completable.fromAction(() -> locationsDbService.update(locationDbModel))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void filterLocations(String pattern, Context context) {
        DisposableManagerUtil.add(Observable.create((ObservableOnSubscribe<List<LocationDbModel>>) emitter -> {
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

    @Override
    public void presentLocationsToView(List<LocationDbModel> locations) {
        if (locations.isEmpty()) {
            view.showEmptyScreen(true);
        } else {
            view.showLocations(locations);
        }
    }
}

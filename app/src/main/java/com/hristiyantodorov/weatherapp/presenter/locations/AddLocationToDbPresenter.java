package com.hristiyantodorov.weatherapp.presenter.locations;

import android.util.Log;

import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.service.LocationsDbService;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AddLocationToDbPresenter extends BasePresenter
        implements AddLocationToDbContracts.Presenter {

    private static final String TAG = "ALTDBPresenter";

    @Inject
    LocationsDbService locationsDbService;

    private AddLocationToDbContracts.View view;

    public AddLocationToDbPresenter(AddLocationToDbContracts.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void saveLocationToDb(String name, Double latitude, Double longitude) {
        Log.d(TAG, "Location Saved");
        subscribeCompletable(Completable.fromAction(() -> locationsDbService.save(
                new LocationDbModel(name, latitude, longitude)))
        );
    }

    @Override
    public void clearDisposables() {
        super.clearDisposables();
    }

    @Override
    protected void inject() {
        provideAppComponent().inject(this);
    }
}

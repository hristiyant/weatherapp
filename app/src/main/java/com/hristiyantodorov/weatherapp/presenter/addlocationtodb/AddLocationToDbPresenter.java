package com.hristiyantodorov.weatherapp.presenter.addlocationtodb;

import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.service.LocationsDbService;
import com.hristiyantodorov.weatherapp.util.Constants;

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
        subscribeCompletable(Completable.fromAction(() -> locationsDbService.save(
                new LocationDbModel(name, latitude, longitude)))
        );
    }

    @Override
    public Boolean isInputValidName(CharSequence input) {
        return input.length() != 0 && input.toString().matches(Constants.REGEX_LOCATION_NAME);
    }

    @Override
    public Boolean isInputValidDouble(CharSequence input) {
        return input.length() != 0 && input.toString().matches(Constants.REGEX_LOCATION_COORDINATES);
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

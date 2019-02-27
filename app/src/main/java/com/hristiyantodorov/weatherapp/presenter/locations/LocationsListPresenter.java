package com.hristiyantodorov.weatherapp.presenter.locations;

import android.util.Log;

import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.util.SearchFilterAsyncTask;

import java.util.List;

public class LocationsListPresenter implements LocationsListContracts.Presenter, DownloadResponse<List<LocationDbModel>> {

    private LocationsListContracts.View view;

    public LocationsListPresenter(LocationsListContracts.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe(LocationsListContracts.View view) {
        this.view = view;
    }

    @Override
    public void loadLocations() {

    }

    @Override
    public void filterLocations(String pattern) {
        new SearchFilterAsyncTask(this).execute(pattern);
    }

    @Override
    public void selectLocation(LocationDbModel selectedLocation) {
        view.showLocationWeatherDetails(selectedLocation);
    }

    @Override
    public void onSuccess(List<LocationDbModel> filteredLocations) {
        view.showLocations(filteredLocations);
        Log.d("SUCC", "Success ");
    }

    @Override
    public void onFailure(Exception e) {
        Log.d("FAIL", "onFailure: ");
    }
}

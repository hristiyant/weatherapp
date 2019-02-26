package com.hristiyantodorov.weatherapp.presenter.locations;

import android.widget.Toast;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;

import java.util.List;
import java.util.concurrent.Executors;

public class LocationsListPresenter implements LocationsListContracts.Presenter {

    private final NetworkingServiceUtil networkingServiceUtil;
    private LocationsListContracts.View view;

    public LocationsListPresenter(NetworkingServiceUtil networkingServiceUtil) {
        this.networkingServiceUtil = networkingServiceUtil;
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
        //view.showLoader(true);
        Executors.newSingleThreadExecutor().execute(() -> {
            List<LocationDbModel> filteredLocations = PersistenceDatabase.getAppDatabase(App.getInstance().getApplicationContext()).locationDao().getLocationsByName(pattern);
            // view.showLoader(true);
        });
    }

    @Override
    public void selectLocation(LocationDbModel location) {
        Toast.makeText(App.getInstance().getApplicationContext(), location.getName(), Toast.LENGTH_SHORT).show();
    }
}

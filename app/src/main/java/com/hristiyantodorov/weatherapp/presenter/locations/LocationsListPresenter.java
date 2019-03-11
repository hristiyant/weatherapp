package com.hristiyantodorov.weatherapp.presenter.locations;

import android.os.AsyncTask;
import android.util.Log;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.util.SearchFilterAsyncTask;

import java.util.List;

public class LocationsListPresenter
        implements LocationsListContracts.Presenter, DownloadResponse<List<LocationDbModel>> {

    private static final String TAG = "LLP";

    private LocationsListContracts.View view;

    public LocationsListPresenter(LocationsListContracts.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void loadLocationsFromDatabase() {
        new LoadLocationsAsyncTask(this).execute();
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
        view.showLoader(false);
        Log.d(TAG, "onSuccess: showLocations");
    }

    @Override
    public void onFailure(Exception e) {
        Log.d(TAG, "onFailure: ");
    }

    static class LoadLocationsAsyncTask extends AsyncTask<Void, Void, List<LocationDbModel>> {
        private static final String TAG = "LLAT";

        private DownloadResponse callback;
        private Exception exception;

        LoadLocationsAsyncTask(DownloadResponse callback) {
            this.callback = callback;
        }

        @Override
        protected void onPostExecute(List<LocationDbModel> result) {
            super.onPostExecute(result);
            if (callback != null) {
                if (exception == null) {
                    callback.onSuccess(result);
                    Log.d(TAG, "onPostExecute: onSuccess");
                } else {
                    callback.onFailure(exception);
                }
            }
        }

        @Override
        protected List<LocationDbModel> doInBackground(Void... voids) {
            return PersistenceDatabase
                    .getAppDatabase(App.getInstance()
                            .getApplicationContext()).locationDao().getAllLocations();
        }
    }
}

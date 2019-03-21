package com.hristiyantodorov.weatherapp.presenter.locations;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.ui.ExceptionHandlerUtil;
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
        new LoadLocationsAsyncTask(this, App.getInstance().getApplicationContext()).execute();
    }

    @Override
    public void filterLocations(String pattern) {
        new SearchFilterAsyncTask(this, App.getInstance().getApplicationContext()).execute(pattern);
    }

    @Override
    public void selectLocation(LocationDbModel selectedLocation) {
        view.showLocationWeatherDetails(selectedLocation);
    }

    @Override
    public void onSuccess(List<LocationDbModel> filteredLocations) {
        if(filteredLocations == null) {
            view.showErrorDialog(getContext(), App.getInstance()
                    .getString(R.string.all_alert_dialog_not_found_message));
        }
        view.showLocations(filteredLocations);
        view.showLoader(false);
        Log.d(TAG, "onSuccess: showLocations");
    }

    @Override
    public void onFailure(Exception e) {
        view.showError(e);
        ExceptionHandlerUtil.logStackTrace(e);
    }

    static class LoadLocationsAsyncTask extends AsyncTask<Void, Void, List<LocationDbModel>> {

        private static final String TAG = "LLAT";

        private DownloadResponse callback;
        private Exception exception;
        private Context context;

        LoadLocationsAsyncTask(DownloadResponse callback, Context context) {
            this.callback = callback;
            this.context = context;
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
                    .getAppDatabase(context).locationDao().getAllLocations();
        }
    }
}

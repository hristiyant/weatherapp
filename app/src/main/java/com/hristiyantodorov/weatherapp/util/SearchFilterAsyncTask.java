package com.hristiyantodorov.weatherapp.util;

import android.os.AsyncTask;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;

import java.util.List;

public class SearchFilterAsyncTask extends AsyncTask<String, Void, List<LocationDbModel>> {

    private DownloadResponse callback;
    private Exception exception;

    public SearchFilterAsyncTask(DownloadResponse callback) {
        this.callback = callback;
    }

    @Override
    protected List<LocationDbModel> doInBackground(String... strings) {
        List<LocationDbModel> locations = PersistenceDatabase.getAppDatabase(App.getInstance().getApplicationContext())
                .locationDao().getAllLocations();



        List<LocationDbModel> filteredLocations = Stream.of(locations)
                .filter(locationDbModel -> locationDbModel.getName().toLowerCase().contains(strings[0]))
                .collect(Collectors.toList());
        return filteredLocations;
    }

    @Override
    protected void onPostExecute(List<LocationDbModel> result) {
//        super.onPostExecute(result);
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess(result);
            } else {
                callback.onFailure(exception);
            }
        }
    }
}

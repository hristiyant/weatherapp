package com.hristiyantodorov.weatherapp.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;

import java.util.List;

public class SearchFilterAsyncTask extends AsyncTask<String, Void, List<LocationDbModel>> {

    private static final String TAG = "SFAT";

    private DownloadResponse callback;
    private Exception exception;
    private Context context;

    public SearchFilterAsyncTask(DownloadResponse callback,Context context) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    protected List<LocationDbModel> doInBackground(String... strings) {
        List<LocationDbModel> locations = PersistenceDatabase.getAppDatabase(context)
                .locationDao().getAllLocations();

        List<LocationDbModel> filteredLocations = Stream.of(locations)
                .filter(locationDbModel -> locationDbModel.getName().toLowerCase().contains(strings[0]))
                .collect(Collectors.toList());
        Log.d(TAG, "doInBackground: filteredLocations.size() = " + filteredLocations.size());
        return filteredLocations;
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
}
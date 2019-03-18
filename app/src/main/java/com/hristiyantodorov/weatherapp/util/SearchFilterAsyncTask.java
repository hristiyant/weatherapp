package com.hristiyantodorov.weatherapp.util;

public class SearchFilterAsyncTask{ //extends AsyncTask<String, Void, List<LocationDbModel>> {
    /*private static final String TAG = "SFAT";

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
    }*/
}
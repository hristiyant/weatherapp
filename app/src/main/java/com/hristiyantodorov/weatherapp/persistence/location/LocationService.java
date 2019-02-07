package com.hristiyantodorov.weatherapp.persistence.location;

import android.os.AsyncTask;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.util.AppExecutorUtil;

import java.util.concurrent.Executor;

public class LocationService implements LocationRepository {

    LocationDao locationDao;
    Executor executor;

    public LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
        executor = AppExecutorUtil.getInstance();
    }

    @Override
    public LocationDbModel getLocationByName(String name) {
        return new LocationDbModel();
    }

    @Override
    public LocationDbModel getLocationById(int id) {
        return new LocationDbModel();
    }

    @Override
    public void insertLocation(LocationDbModel... locations) {
        new InsertLocationInDatabase().doInBackground(locations);
    }

    @Override
    public void deleteLocation(Integer... ids) {
        new DeleteLocationFromDatabase().doInBackground(ids);
    }

    public class InsertLocationInDatabase extends AsyncTask<LocationDbModel, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(LocationDbModel... locations) {
            LocationDao locationDao = PersistenceDatabase
                    .getAppDatabase(App.getInstance().getApplicationContext()).locationDao();
            for (LocationDbModel location : locations) {
                locationDao.insertLocation(location);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }

    public class DeleteLocationFromDatabase extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            LocationDao locationDao = PersistenceDatabase
                    .getAppDatabase(App.getInstance().getApplicationContext()).locationDao();
            for (Integer id : ids) {
                locationDao.deleteLocation(id);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }
}
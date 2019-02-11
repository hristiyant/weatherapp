package com.hristiyantodorov.weatherapp.persistence.location;

import android.os.AsyncTask;

import com.hristiyantodorov.weatherapp.util.AppExecutorUtil;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class LocationService implements LocationRepository {
    private static LocationDao locationDao;
    private Executor executor;

    public LocationService(LocationDao locationDao) {
        LocationService.locationDao = locationDao;
        executor = AppExecutorUtil.getInstance();
    }

    @Override
    public List<LocationDbModel> getAllLocations() {
        try {
            return new GetAllLocationsAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LocationDbModel getLocationByName(String name) {
        try {
            return new GetLocationByNameAsyncTask().execute(name).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LocationDbModel getLocationById(int id) {
        try {
            return new GetLocationByIdAsyncTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertLocations(LocationDbModel... locations) {
        executor.execute(() -> locationDao.insertLocations(locations));
    }

    @Override
    public void updateLocations(LocationDbModel... locations) {
        executor.execute(() -> locationDao.updateLocations(locations));
    }

    @Override
    public void deleteLocations(LocationDbModel... locations) {
        executor.execute(() -> locationDao.deleteLocations(locations));
    }

    public static class GetAllLocationsAsyncTask extends AsyncTask<Void, Integer, List<LocationDbModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<LocationDbModel> doInBackground(Void... voids) {
            return locationDao.getAllLocations();
        }

        @Override
        protected void onPostExecute(List<LocationDbModel> locationDbModels) {
            super.onPostExecute(locationDbModels);
        }
    }

    public static class GetLocationByNameAsyncTask extends AsyncTask<String, Integer, LocationDbModel> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LocationDbModel doInBackground(String... strings) {
            return locationDao.getLocationByName(strings[0]);
        }

        @Override
        protected void onPostExecute(LocationDbModel locationDbModel) {
            super.onPostExecute(locationDbModel);
        }
    }

    public static class GetLocationByIdAsyncTask extends AsyncTask<Integer, Void, LocationDbModel> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LocationDbModel doInBackground(Integer... integers) {
            return locationDao.getLocationById(1);
        }

        @Override
        protected void onPostExecute(LocationDbModel locationDbModel) {
            super.onPostExecute(locationDbModel);
        }
    }
}
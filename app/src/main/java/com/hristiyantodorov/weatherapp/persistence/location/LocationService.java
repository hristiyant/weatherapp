package com.hristiyantodorov.weatherapp.persistence.location;

import android.os.AsyncTask;

import com.hristiyantodorov.weatherapp.repository.LocationRepository;
import com.hristiyantodorov.weatherapp.util.AppExecutorUtil;
import com.hristiyantodorov.weatherapp.util.AsyncResponse;

import java.util.List;
import java.util.concurrent.Executor;

public class LocationService implements LocationRepository {

    private static LocationDao locationDao;
    private Executor executor;

    public LocationService(LocationDao locationDao) {
        LocationService.locationDao = locationDao;
        executor = AppExecutorUtil.getInstance();
    }

    @Override
    public List<LocationDbModel> getAllLocations(AsyncResponse response) {
        new GetAllLocationsAsyncTask(response).execute();
        return null;
    }

    @Override
    public LocationDbModel getLocationByName(String name, AsyncResponse response) {
        new GetLocationByNameAsyncTask(response).execute(name);
        return null;
    }

    @Override
    public LocationDbModel getLocationById(int id, AsyncResponse response) {
        new GetLocationByIdAsyncTask(response).execute(id);
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

    private static class GetAllLocationsAsyncTask extends AsyncTask<Void, Void, List<LocationDbModel>> {

        private AsyncResponse response;
        private Exception exception;

        GetAllLocationsAsyncTask(AsyncResponse response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<LocationDbModel> doInBackground(Void... voids) {
            try {
                return locationDao.getAllLocations();
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<LocationDbModel> locations) {
            if (response != null) {
                if (exception == null) {
                    response.onSuccess(locations);
                } else {
                    response.onFailure(exception);
                }
            }
        }
    }

    private static class GetLocationByNameAsyncTask extends AsyncTask<String, Void, LocationDbModel> {
        private AsyncResponse response;
        private Exception exception;

        GetLocationByNameAsyncTask(AsyncResponse response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LocationDbModel doInBackground(String... names) {
            try {
                return locationDao.getLocationByName(names[0]);
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(LocationDbModel location) {
            if (response != null) {
                if (exception == null) {
                    response.onSuccess(location);
                } else {
                    response.onFailure(exception);
                }
            }
        }

    }

    private static class GetLocationByIdAsyncTask extends AsyncTask<Integer, Void, LocationDbModel> {

        private AsyncResponse response;
        private Exception exception;

        GetLocationByIdAsyncTask(AsyncResponse response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LocationDbModel doInBackground(Integer... ids) {
            try {
                return locationDao.getLocationById(ids[0]);
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(LocationDbModel location) {
            if (response != null) {
                if (exception == null) {
                    response.onSuccess(location);
                } else {
                    response.onFailure(exception);
                }
            }
        }
    }
}
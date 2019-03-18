package com.hristiyantodorov.weatherapp.persistence.user;

import android.os.AsyncTask;

import com.hristiyantodorov.weatherapp.repository.UserRepository;
import com.hristiyantodorov.weatherapp.util.AppExecutorUtil;
import com.hristiyantodorov.weatherapp.util.AsyncResponse;

import java.util.List;
import java.util.concurrent.Executor;

public class UserService implements UserRepository {

    private static UserDao userDao;
    private Executor executor;
    public UserService(UserDao userDao) {
        UserService.userDao = userDao;
        executor = AppExecutorUtil.getInstance();
    }

    @Override
    public List<UserDbModel> getAllUsers(AsyncResponse response) {
        new GetAllUsersAsyncTask(response).execute();
        return null;
    }

    @Override
    public UserDbModel getUserByEmail(String email, AsyncResponse response) {
        new GetUserByEmailAsyncTask(response).execute(email);
        return null;
    }

    @Override
    public UserDbModel getUserById(int id, AsyncResponse response) {
        new GetUserByIdAsyncTask(response).execute(id);
        return null;
    }

    @Override
    public void insertUsers(UserDbModel... users) {
        executor.execute(() -> userDao.insertUsers(users));
    }

    @Override
    public void deleteUsers(UserDbModel... users) {
        executor.execute(() -> userDao.deleteUsers(users));
    }

    @Override
    public void deleteUserById(int id) {
        executor.execute(() -> userDao.deleteUserById(id));
    }

    private static class GetAllUsersAsyncTask extends AsyncTask<Void, Integer, List<UserDbModel>> {

        private AsyncResponse response;
        private Exception exception;

        GetAllUsersAsyncTask(AsyncResponse response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<UserDbModel> doInBackground(Void... voids) {
            try {
                return userDao.getAllUsers();
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<UserDbModel> users) {
            if (response != null) {
                if (exception == null) {
                    response.onSuccess(users);
                } else {
                    response.onFailure(exception);
                }
            }
        }
    }

    private static class GetUserByEmailAsyncTask extends AsyncTask<String, Void, UserDbModel> {

        private AsyncResponse response;
        private Exception exception;

        GetUserByEmailAsyncTask(AsyncResponse response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected UserDbModel doInBackground(String... emails) {
            try {
                return userDao.getUserByEmail(emails[0]);
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(UserDbModel user) {
            if (response != null) {
                if (exception == null) {
                    response.onSuccess(user);
                } else {
                    response.onFailure(exception);
                }
            }
        }
    }

    private static class GetUserByIdAsyncTask extends AsyncTask<Integer, Void, UserDbModel> {

        private AsyncResponse response;
        private Exception exception;

        GetUserByIdAsyncTask(AsyncResponse response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected UserDbModel doInBackground(Integer... ids) {
            try {
                return userDao.getUserById(ids[0]);
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(UserDbModel user) {
            if (response != null) {
                if (exception == null) {
                    response.onSuccess(user);
                } else {
                    response.onFailure(exception);
                }
            }
        }
    }
}
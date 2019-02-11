package com.hristiyantodorov.weatherapp.persistence.user;

import android.os.AsyncTask;

import com.hristiyantodorov.weatherapp.util.AppExecutorUtil;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class UserService implements UserRepository {
    private static UserDao userDao;
    private Executor executor;

    public UserService(UserDao userDao) {
        UserService.userDao = userDao;
        executor = AppExecutorUtil.getInstance();
    }

    @Override
    public List<UserDbModel> getAllUsers() {
        try {
            return new GetAllUsersAsyncTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDbModel getUserByEmail(String email) {
        try {
            return new GetUserByEmailAsyncTask().execute(email).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDbModel getUserById(int id) {
        try {
            return new GetUserByIdAsyncTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public static class GetAllUsersAsyncTask extends AsyncTask<Void, Integer, List<UserDbModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<UserDbModel> doInBackground(Void... voids) {
            return userDao.getAllUsers();
        }

        @Override
        protected void onPostExecute(List<UserDbModel> userDbModels) {
            super.onPostExecute(userDbModels);
        }
    }

    public static class GetUserByEmailAsyncTask extends AsyncTask<String, Integer, UserDbModel> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected UserDbModel doInBackground(String... emails) {
            return userDao.getUserByEmail(emails[0]);
        }

        @Override
        protected void onPostExecute(UserDbModel userDbModel) {
            super.onPostExecute(userDbModel);
        }
    }

    public static class GetUserByIdAsyncTask extends AsyncTask<Integer, Integer, UserDbModel> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected UserDbModel doInBackground(Integer... ids) {
            return userDao.getUserById(ids[0]);
        }

        @Override
        protected void onPostExecute(UserDbModel userDbModel) {
            super.onPostExecute(userDbModel);
        }
    }
}
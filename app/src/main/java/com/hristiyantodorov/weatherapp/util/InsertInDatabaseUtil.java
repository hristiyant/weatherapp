package com.hristiyantodorov.weatherapp.util;

import android.os.AsyncTask;
import android.util.Log;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.persistence.user.UserDao;
import com.hristiyantodorov.weatherapp.persistence.user.UserDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;

import static android.support.constraint.Constraints.TAG;

public class InsertInDatabaseUtil extends AsyncTask<UserDbModel, Integer, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(UserDbModel... users) {

        int progress = 0;
        publishProgress(progress);

        UserDao userDao = PersistenceDatabase
                .getAppDatabase(App.getInstance().getApplicationContext()).userDao();
        for (UserDbModel user : users) {
            userDao.insertUser(user);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d(TAG, "You are in progress update ... " + values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }
}

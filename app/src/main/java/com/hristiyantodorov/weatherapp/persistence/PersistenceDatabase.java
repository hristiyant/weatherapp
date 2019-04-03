package com.hristiyantodorov.weatherapp.persistence;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.AppDatabase;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDao;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDao;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;

import java.util.Arrays;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersistenceDatabase {

    private AppDatabase appDatabase;

    @Inject
    public PersistenceDatabase(Application application) {
        this.appDatabase = Room.databaseBuilder(application, AppDatabase.class, "app_db")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Executors.newSingleThreadExecutor()
                                .execute(() -> getLocationDao()
                                        .insertAll(Arrays.asList(
                                                new LocationDbModel(application.getResources().getString(R.string.persistence_database_city_name_tokyo), 35.652832, 139.839478),
                                                new LocationDbModel(application.getResources().getString(R.string.persistence_database_city_name_new_york), 40.730610, -73.935242),
                                                new LocationDbModel(application.getResources().getString(R.string.persistence_database_city_name_paris), 48.864716, 2.349014),
                                                new LocationDbModel(application.getResources().getString(R.string.persistence_database_city_name_london), 51.509865, -0.118092),
                                                new LocationDbModel(application.getResources().getString(R.string.persistence_database_city_name_sydney), -33.865143, 151.209900),
                                                new LocationDbModel(application.getResources().getString(R.string.persistence_database_city_name_sliven), 42.68583, 26.32917),
                                                new LocationDbModel(application.getResources().getString(R.string.persistence_database_city_name_sofia), 42.69751, 23.32415))));
                    }
                }).build();
    }

    public AppDatabase getDatabase() {
        return appDatabase;
    }

    public LocationDao getLocationDao() {
        return appDatabase.locationDao();
    }

    public ForecastFullDao getForecastFullDao() {
        return appDatabase.forecastFullDao();
    }
}
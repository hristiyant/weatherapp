package com.hristiyantodorov.weatherapp.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.AppDatabase;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;

import java.util.concurrent.Executors;

public class PersistenceDatabase {

    private static AppDatabase appDatabase;
    private static final Object LOCK = new Object();

    public synchronized static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            synchronized (LOCK) {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context, AppDatabase.class, "app_db")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(
                                            () -> appDatabase
                                                    .locationDao()
                                                    .insertLocations(new LocationDbModel(context.getString(R.string.persistence_database_city_name_tokyo), 35.652832, 139.839478),
                                                            new LocationDbModel(context.getString(R.string.persistence_database_city_name_new_york), 40.730610, -73.935242),
                                                            new LocationDbModel(context.getString(R.string.persistence_database_city_name_paris), 48.864716, 2.349014),
                                                            new LocationDbModel(context.getString(R.string.persistence_database_city_name_london), 51.509865, -0.118092),
                                                            new LocationDbModel(context.getString(R.string.persistence_database_city_name_sydney), -33.865143, 151.209900))
                                    );
                                }
                            })
                            .build();
                }
            }
        }
        return appDatabase;
    }
}
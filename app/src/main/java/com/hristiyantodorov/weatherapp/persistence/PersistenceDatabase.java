package com.hristiyantodorov.weatherapp.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.hristiyantodorov.weatherapp.model.AppDatabase;
import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;

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
                                                    .insertAll(LocationDbModel.prePopulateLocationsList())
                                    );
//                                    AppExecutorUtil.getInstance().execute(
//                                            () -> appDatabase
//                                                    .forecastFullDao()
//                                                    .insert(new ForecastFullDbModel()));
                                }
                            })
                            .build();
                }
            }
        }
        return appDatabase;
    }
}
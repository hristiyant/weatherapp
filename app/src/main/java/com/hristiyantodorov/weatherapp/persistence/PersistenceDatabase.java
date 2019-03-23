package com.hristiyantodorov.weatherapp.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.hristiyantodorov.weatherapp.model.AppDatabase;

public class PersistenceDatabase {

    private static AppDatabase appDatabase;
    private static final Object LOCK = new Object();

    public synchronized static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            synchronized (LOCK) {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context, AppDatabase.class, "app_db")
                            /*.addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadExecutor().execute(
                                            () -> appDatabase
                                                    .locationDao()
                                                    .insertAll(LocationDbModel.prePopulateLocationsList())
                                    );
                                }
                            })*/
                            .build();
                }
            }
        }
        return appDatabase;
    }
}
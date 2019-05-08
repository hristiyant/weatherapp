package com.hristiyantodorov.weatherapp;

import android.app.Application;
import android.content.res.Resources;

import com.hristiyantodorov.weatherapp.di.AppComponent;
import com.hristiyantodorov.weatherapp.di.DaggerAppComponent;
import com.hristiyantodorov.weatherapp.di.module.ApplicationModule;

public class App extends Application {

    private static App instance;
    private AppComponent appComponent;
    private static Resources res;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        res = getResources();
    }

    public static App getInstance() {
        return instance;
    }

    public static Resources getRes() {
        return res;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return appComponent;
    }
}

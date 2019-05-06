package com.hristiyantodorov.weatherapp;

import android.app.Application;

import com.hristiyantodorov.weatherapp.di.AppComponent;
import com.hristiyantodorov.weatherapp.di.DaggerAppComponent;
import com.hristiyantodorov.weatherapp.di.module.ApplicationModule;

public class App extends Application {

    private static App instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
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

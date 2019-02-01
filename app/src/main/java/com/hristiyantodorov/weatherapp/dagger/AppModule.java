package com.hristiyantodorov.weatherapp.dagger;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@Module
abstract class AppModule {
    @Binds
    abstract Context bindContext(Application application);
}
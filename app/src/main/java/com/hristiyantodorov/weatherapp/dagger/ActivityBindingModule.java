package com.hristiyantodorov.weatherapp.dagger;

import com.hristiyantodorov.weatherapp.ui.activity.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(
            modules = LoginModule.class
    )
    abstract LoginActivity loginActivity();
}

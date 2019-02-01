package com.hristiyantodorov.weatherapp.dagger;

import com.hristiyantodorov.weatherapp.presenter.login.LoginContracts;
import com.hristiyantodorov.weatherapp.presenter.login.LoginPresenter;
import com.hristiyantodorov.weatherapp.ui.fragment.login.LoginFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class LoginModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginFragment loginFragment();

    @ActivityScoped
    @Binds
    abstract LoginContracts.Presenter loginPresenter(LoginPresenter presenter);
}
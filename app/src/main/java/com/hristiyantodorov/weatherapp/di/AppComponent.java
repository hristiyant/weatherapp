package com.hristiyantodorov.weatherapp.di;

import com.hristiyantodorov.weatherapp.di.module.ApplicationModule;
import com.hristiyantodorov.weatherapp.presenter.locations.AddLocationToDbPresenter;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListPresenter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.activity.WeatherDetailsActivityPresenter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily.ForecastDailyPresenter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly.ForecastHourlyPresenter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.fragment.WeatherDetailsFragmentPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class
})
public interface AppComponent {

    void inject(LocationsListPresenter presenter);

    void inject(WeatherDetailsActivityPresenter presenter);

    void inject(ForecastDailyPresenter presenter);

    void inject(ForecastHourlyPresenter presenter);

    void inject(WeatherDetailsFragmentPresenter presenter);

    void inject(AddLocationToDbPresenter presenter);
}
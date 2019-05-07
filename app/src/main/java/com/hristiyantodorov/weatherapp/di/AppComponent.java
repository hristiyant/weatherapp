package com.hristiyantodorov.weatherapp.di;

import com.hristiyantodorov.weatherapp.di.module.ApplicationModule;
import com.hristiyantodorov.weatherapp.presenter.addlocationtodb.AddLocationToDbPresenter;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListPresenter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetailsactivity.WeatherDetailsActivityPresenter;
import com.hristiyantodorov.weatherapp.presenter.forecastdaily.ForecastDailyPresenter;
import com.hristiyantodorov.weatherapp.presenter.forecasthourly.ForecastHourlyPresenter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetailsfragment.WeatherDetailsFragmentPresenter;

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
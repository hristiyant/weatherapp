package com.hristiyantodorov.weatherapp.adapter.weatherdetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.GetForecastDataInteractorImpl;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.WeatherDetailsContracts;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.WeatherDetailsPresenter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily.ForecastDailyContracts;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily.ForecastDailyPresenter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly.ForecastHourlyContracts;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly.ForecastHourlyPresenter;
import com.hristiyantodorov.weatherapp.ui.fragment.settings.SettingsFragment;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.ForecastDailyFragment;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.ForecastHourlyFragment;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.WeatherDetailsFragment;

public class WeatherDetailsPagerAdapter extends FragmentPagerAdapter {
    private static final int FORECAST_TAB_DETAILED = 0;
    private static final int FORECAST_TAB_HOURLY = 1;
    private static final int FORECAST_TAB_DAILY = 2;
    private static final int FORECAST_TAB_SETTINGS = 3;
    private static final int NUMBER_OF_ELEMENTS = 4;

    public WeatherDetailsPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    ForecastHourlyContracts.Presenter forecastHourlyPresenter;
    ForecastDailyContracts.Presenter forecastDailyPresenter;
    WeatherDetailsContracts.Presenter weatherDetailsPresenter;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FORECAST_TAB_DETAILED:
                WeatherDetailsFragment weatherDetailsFragment = WeatherDetailsFragment.newInstance();
                weatherDetailsPresenter = new WeatherDetailsPresenter(
                        weatherDetailsFragment,
                        new GetForecastDataInteractorImpl()
                );
                return weatherDetailsFragment;
            case FORECAST_TAB_HOURLY:
                ForecastHourlyFragment forecastHourlyFragment = ForecastHourlyFragment.newInstance();
                forecastHourlyPresenter = new ForecastHourlyPresenter(forecastHourlyFragment);
                return forecastHourlyFragment;
            case FORECAST_TAB_DAILY:
                ForecastDailyFragment forecastDailyFragment = ForecastDailyFragment.newInstance();
                forecastDailyPresenter = new ForecastDailyPresenter(forecastDailyFragment);
                return forecastDailyFragment;
            case FORECAST_TAB_SETTINGS:
                return SettingsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_ELEMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case FORECAST_TAB_DETAILED:
                return getStringFromResources(R.string.weather_details_tab_detailed_title);
            case FORECAST_TAB_HOURLY:
                return getStringFromResources(R.string.weather_details_tab_hourly_title);
            case FORECAST_TAB_DAILY:
                return getStringFromResources(R.string.weather_details_tab_daily_title);
            case FORECAST_TAB_SETTINGS:
                return getStringFromResources(R.string.weather_details_tab_settings_title);
            default:
                return null;
        }
    }

    private String getStringFromResources(int resId) {
        return App.getInstance().getString(resId);
    }
}
package com.hristiyantodorov.weatherapp.adapters.weatherdetails;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.ForecastFragment;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.WeatherDetailsFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class WeatherDetailsPagerAdapter extends FragmentPagerAdapter {
    private static final int FORECAST_TAB_HOURLY = 0;
    private static final int FORECAST_TAB_DAILY = 1;
    private static final int FORECAST_TAB_DETAILED = 2;
    private static final int NUMBER_OF_ELEMENTS = 3;

    public WeatherDetailsPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FORECAST_TAB_HOURLY:
            case FORECAST_TAB_DAILY:
                return ForecastFragment.newInstance();
            case FORECAST_TAB_DETAILED:
                return WeatherDetailsFragment.newInstance();
            default:
                 throw new IllegalArgumentException("Message");
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_ELEMENTS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case FORECAST_TAB_HOURLY:
                return App.getInstance().getString(R.string.weather_details_tab_hourly_title);
            case FORECAST_TAB_DAILY:
                return App.getInstance().getString(R.string.weather_details_tab_daily_title);
            case FORECAST_TAB_DETAILED:
                return App.getInstance().getString(R.string.weather_details_tab_detailed_title);
            default:
                return null;
        }
    }
}
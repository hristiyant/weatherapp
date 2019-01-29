package com.hristiyantodorov.weatherapp.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.ForecastFragment;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.WeatherDetailsFragment;

public class WeatherDetailsPagerAdapter extends FragmentPagerAdapter {
    public static final int FORECAST_TAB_HOURLY = 0;
    public static final int FORECAST_TAB_DAILY = 1;
    public static final int FORECAST_TAB_DETAILED = 2;
    public static final int NUMBER_OF_ELEMENTS = 3;

    public WeatherDetailsPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FORECAST_TAB_HOURLY:
            case FORECAST_TAB_DAILY:
                return ForecastFragment.newInstance();
            case FORECAST_TAB_DETAILED:
                return WeatherDetailsFragment.newInstance();
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

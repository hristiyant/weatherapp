package com.hristiyantodorov.weatherapp.ui.activity.weatherdetails;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.WeatherDetailsPagerAdapter;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;

import butterknife.BindView;

public class WeatherDetailsActivity extends BaseActivity {

    @BindView(R.id.view_pager_forecasts_holder)
    ViewPager viewPager;

    @BindView(R.id.tab_layout_forecast_categories)
    TabLayout tabLayout;

    @BindView(R.id.img_current_weather_icon)
    ImageView imgWeatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WeatherDetailsPagerAdapter weatherDetailsPagerAdapter = new WeatherDetailsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(weatherDetailsPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        // TODO: 1/29/2019 Load image according to data from API (currently using test vector)
        imgWeatherIcon.setImageResource(R.drawable.ic_weather_cloudy);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_weather_details;
    }
}
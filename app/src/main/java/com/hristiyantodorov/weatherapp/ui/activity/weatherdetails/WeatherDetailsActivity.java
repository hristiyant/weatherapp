package com.hristiyantodorov.weatherapp.ui.activity.weatherdetails;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.WeatherDetailsPagerAdapter;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import butterknife.BindView;

public class WeatherDetailsActivity extends BaseActivity implements DownloadResponse<WeatherData> {
    private static final int DOUBLE_ROUND_PLACES = 1;

    @BindView(R.id.view_pager_forecasts_holder)
    ViewPager viewPager;
    @BindView(R.id.tab_layout_forecast_categories)
    TabLayout tabLayout;
    @BindView(R.id.img_current_weather_icon)
    ImageView imgWeatherIcon;
    @BindView(R.id.txt_current_temperature)
    TextView txtCurrentTemp;
    @BindView(R.id.txt_summary)
    TextView txtSummary;
    @BindView(R.id.txt_last_updated)
    TextView txtLastUpdated;

    WeatherData weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkingServiceUtil util = new NetworkingServiceUtil();
        util.getWeatherData(WeatherDetailsActivity.this);

        WeatherDetailsPagerAdapter weatherDetailsPagerAdapter = new WeatherDetailsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(weatherDetailsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        imgWeatherIcon.setImageResource(R.drawable.ic_weather_cloudy);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_weather_details;
    }

    @Override
    public void onSuccess(WeatherData object) {
        weatherData = object;
        txtSummary.setText(weatherData.getCurrently().getSummary());
        double temperature = convertFahrenheitToCelsius(weatherData.getCurrently().getTemperature());
        txtCurrentTemp.setText(String.valueOf(roundDoubleNum(temperature, DOUBLE_ROUND_PLACES))
                + getString(R.string.txt_current_temp_celsius));
        String timeStamp = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new java.util.Date());
        txtLastUpdated.setText(R.string.txt_last_updated + timeStamp);
    }

    @Override
    public void onFailure(Exception e) {
        // TODO: 2/15/2019 Add showErrorDialog when available.
    }

    private double convertFahrenheitToCelsius(double degreesFahrenheit) {
        return (5.0 / 9.0) * (degreesFahrenheit - 32.0);
    }

    private double roundDoubleNum(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
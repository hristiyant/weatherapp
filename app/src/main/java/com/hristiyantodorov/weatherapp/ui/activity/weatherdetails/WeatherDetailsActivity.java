package com.hristiyantodorov.weatherapp.ui.activity.weatherdetails;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.WeatherDetailsPagerAdapter;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingService;
import com.hristiyantodorov.weatherapp.ui.ExceptionHandlerUtil;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;

public class WeatherDetailsActivity extends BaseActivity implements DownloadResponse<WeatherData>, LocationListener {

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
    @BindView(R.id.txt_location_name)
    TextView txtLocationName;
    @BindView(R.id.txt_wind_speed)
    TextView txtWindSpeed;

    private WeatherData weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentLocationLat = SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null);
        String currentLocationLon = SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null);
        new NetworkingService().getWeatherDataCurrently(WeatherDetailsActivity.this, currentLocationLat, currentLocationLon);

        WeatherDetailsPagerAdapter weatherDetailsPagerAdapter =
                new WeatherDetailsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(weatherDetailsPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                // TODO: 19.3.2019 CURRENTLY NOT BEING USED
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                // TODO: 19.3.2019 CURRENTLY NOT BEING USED
            }
        });
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_weather_details;
    }

    @Override
    public void onSuccess(WeatherData result) {
        weatherData = result;
        txtLocationName.setText(SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_NAME,
                "Current Location"));
        txtSummary.setText(weatherData.getCurrently().getSummary());
        txtCurrentTemp.setText(Html.fromHtml(WeatherDataFormatterUtil
                .convertFahrenheitToCelsius(result.getCurrently().getTemperature()))
                + "<sup>\u00B0c</sup>");
        String currentTimeStamp = DateFormat
                .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
                .format(new java.util.Date());
        txtLastUpdated.setText(getString(R.string.txt_last_updated, currentTimeStamp));
        txtWindSpeed.setText(getString(R.string.txt_current_wind_speed, WeatherDataFormatterUtil.convertMphToMs(result.getCurrently().getWindSpeed())));
        imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(result.getCurrently()
                .getIcon()));
    }

    @Override
    public void onFailure(Exception e) {
        ExceptionHandlerUtil.throwException(e);
        ExceptionHandlerUtil.logStackTrace(e);
    }

    public void refreshLastUpdated() {
        String timeStamp = DateFormat
                .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
                .format(new java.util.Date());
        txtLastUpdated.setText(getString(R.string.txt_last_updated, timeStamp));
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO: 2/22/2019 CURRENTLY NOT USED
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO: 2/22/2019 CURRENTLY NOT USED
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO: 2/22/2019 CURRENTLY NOT USED
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO: 2/22/2019 CURRENTLY NOT USED
    }
}
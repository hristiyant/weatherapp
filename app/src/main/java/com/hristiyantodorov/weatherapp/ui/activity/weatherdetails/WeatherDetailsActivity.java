package com.hristiyantodorov.weatherapp.ui.activity.weatherdetails;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;

public class WeatherDetailsActivity extends BaseActivity implements DownloadResponse<WeatherData>, LocationListener {
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
    @BindView(R.id.txt_location_name)
    TextView txtLocationName;
    @BindView(R.id.txt_wind_speed)
    TextView txtWindSpeed;

    WeatherData weatherData;

    LocationManager locationManager;

    LocationDbModel finalLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentLocationName = SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_NAME, null);
        String currentLocationLat = SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null);
        String currentLocationLon = SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null);
        new NetworkingServiceUtil().getWeatherDataCurrently(WeatherDetailsActivity.this, currentLocationLat, currentLocationLon);

        WeatherDetailsPagerAdapter weatherDetailsPagerAdapter =
                new WeatherDetailsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(weatherDetailsPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

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
        txtLocationName.setText(SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_NAME, "Current Location"));
        txtSummary.setText(weatherData.getCurrently().getSummary());
        double temperature = convertFahrenheitToCelsius(weatherData.getCurrently().getTemperature());
//        txtCurrentTemp.setText(getString(R.string.txt_current_temp_celsius, String.valueOf(roundDoubleNum(temperature, DOUBLE_ROUND_PLACES))));
        txtCurrentTemp.setText(Html.fromHtml(String.valueOf(roundDoubleNum(temperature, DOUBLE_ROUND_PLACES)) + "<sup>\u00B0c</sup>"));
        String currentTimeStamp = DateFormat
                .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
                .format(new java.util.Date());
        txtLastUpdated.setText(getString(R.string.txt_last_updated, currentTimeStamp));
        txtWindSpeed.setText(getString(R.string.txt_current_wind_speed,WeatherDataFormatterUtil.convertMphToMs(result.getCurrently().getWindSpeed())));
        imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(result.getCurrently().getIcon()));
    }

    @Override
    public void onFailure(Exception e) {
        // TODO: 3/1/2019 CURRENTLY NOT BEING USED
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
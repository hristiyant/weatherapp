package com.hristiyantodorov.weatherapp.ui.activity.weatherdetails;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.WeatherDetailsPagerAdapter;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

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

    WeatherData weatherData;

    LocationManager locationManager;
    boolean canGetLocation;
    Location finalLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        finalLocation = getLocation();
        SharedPrefUtil.write("MY_LATITUDE", String.valueOf(finalLocation.getLatitude()));
        SharedPrefUtil.write("MY_LONGITUDE", String.valueOf(finalLocation.getLongitude()));

        new NetworkingServiceUtil().getWeatherDataCurrently(WeatherDetailsActivity.this);

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
        txtSummary.setText(weatherData.getCurrently().getSummary());
        double temperature = convertFahrenheitToCelsius(weatherData.getCurrently().getTemperature());
//        txtCurrentTemp.setText(getString(R.string.txt_current_temp_celsius, String.valueOf(roundDoubleNum(temperature, DOUBLE_ROUND_PLACES))));
        txtCurrentTemp.setText(Html.fromHtml(String.valueOf(roundDoubleNum(temperature, DOUBLE_ROUND_PLACES)) + "<sup>\u00B0c</sup>"));
        String currentTimeStamp = DateFormat
                .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
                .format(new java.util.Date());
        txtLastUpdated.setText(getString(R.string.txt_last_updated, currentTimeStamp));

        int drawableRes = 0;
        switch (result.getCurrently().getIcon()) {
            case "clear-day":
                drawableRes = R.drawable.ic_clear_day;
                break;
            case "clear-night":
                drawableRes = R.drawable.ic_clear_night;
                break;
            case "rain":
                drawableRes = R.drawable.ic_rain;
                break;
            case "snow":
                drawableRes = R.drawable.ic_snow;
                break;
            case "sleet":
                drawableRes = R.drawable.ic_sleet;
                break;
            case "wind":
                drawableRes = R.drawable.ic_wind;
                break;
            case "fog":
                drawableRes = R.drawable.ic_fog;
                break;
            case "cloudy":
                drawableRes = R.drawable.ic_cloudy;
                break;
            case "partly-cloudy-day":
                drawableRes = R.drawable.ic_partly_cloudy_day;
                break;
            case "partly-cloudy-night":
                drawableRes = R.drawable.ic_partly_cloudy_night;
                break;
        }
        imgWeatherIcon.setImageResource(drawableRes);
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

    public Location getLocation() {
        Location location = null;/*
        double latitude;
        double longitude;*/
        try {
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        /*if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }*/
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                           /* if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }*/
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
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
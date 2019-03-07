package com.hristiyantodorov.weatherapp.ui.activity.weatherdetails;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.WeatherDetailsPagerAdapter;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;
import com.hristiyantodorov.weatherapp.util.cache.InternalStorageCache;
import com.hristiyantodorov.weatherapp.util.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.util.retrofit.APIInterface;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastCurrentlyResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDetailsActivity extends BaseActivity implements Callback<ForecastFullResponse> {

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
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        String currentLocationName = SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_NAME, "Current location");
        String currentLocationLat = SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null);
        String currentLocationLon = SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<ForecastFullResponse> call = apiInterface.getForecastCurrently(currentLocationLat, currentLocationLon);
        call.enqueue(this);

        WeatherDetailsPagerAdapter weatherDetailsPagerAdapter =
                new WeatherDetailsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(weatherDetailsPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                // TODO: 3/6/2019 CURRENTLY NOT BEING USED
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                // TODO: 3/6/2019 CURRENTLY NOT BEING USED
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_weather_details;
    }

    private void setFields(ForecastCurrentlyResponse response, String timezone) {
        txtLocationName.setText(timezone);
        txtSummary.setText(response.getSummary());
        txtCurrentTemp.setText(Html.fromHtml(
                WeatherDataFormatterUtil
                        .convertFahrenheitToCelsius(response.getTemperature())
                        + "<sup>\u00B0c</sup>"
        ));
        txtWindSpeed.setText(getString(
                R.string.txt_current_wind_speed,
                WeatherDataFormatterUtil.convertMphToMs(response.getWindSpeed())
        ));
        String currentTimeStamp = DateFormat
                .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
                .format(new java.util.Date());
        txtLastUpdated.setText(getString(R.string.txt_last_updated, currentTimeStamp));
        imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(response.getIcon()));
        progressBar.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }

    public void refreshLastUpdated() {
        String timeStamp = DateFormat
                .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
                .format(new java.util.Date());
        txtLastUpdated.setText(getString(R.string.txt_last_updated, timeStamp));
    }

    @Override
    public void onResponse(Call<ForecastFullResponse> call, Response<ForecastFullResponse> response) {
        int statusCode = response.code();
        Log.d("WDActivity", "response code: " + statusCode);
        ForecastFullResponse fullResponse = response.body();
        setFields(fullResponse.getCurrently(), fullResponse.getTimezone());
        try {
            InternalStorageCache.writeObject(this, fullResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<ForecastFullResponse> call, Throwable t) {
        Log.e("WDActivity", "onFailure: ");
    }

}
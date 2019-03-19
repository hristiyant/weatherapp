package com.hristiyantodorov.weatherapp.ui.activity.weatherdetails;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.WeatherDetailsPagerAdapter;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.WeatherDetailsActivityContracts;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.WeatherDetailsActivityPresenter;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;

public class WeatherDetailsActivity extends BaseActivity
        implements WeatherDetailsActivityContracts.View,
        SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "WDActivity";

    @BindView(R.id.txt_forecast_not_available)
    TextView txtForecastNotAvailable;
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
    @BindView(R.id.img_current_weather_icon)
    ImageView imgWeatherIcon;
    @BindView(R.id.view_pager_forecasts_holder)
    ViewPager viewPager;
    @BindView(R.id.tab_layout_forecast_categories)
    TabLayout tabLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
//    @BindView(R.id.swipe_container)
//    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.app_bar_basic_forecast)
    AppBarLayout appBarLayout;

    private WeatherDetailsActivityContracts.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        swipeRefreshLayout.setOnRefreshListener(this);

        new WeatherDetailsActivityPresenter(this);
        presenter.downloadForecastFromApi();

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

    @Override
    protected void onResume() {
        super.onResume();
//        appBarLayout.addOnOffsetChangedListener(this);
        refreshLastUpdated();
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }*/

    @Override
    public void onRefresh() {
//        swipeRefreshLayout.setRefreshing(true);
        presenter.downloadForecastFromApi();
//        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(WeatherDetailsActivityContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoader(boolean isShowing) {
        progressBar.setVisibility(isShowing ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmptyScreen(boolean isShowing) {
        txtForecastNotAvailable.setVisibility(isShowing ? View.VISIBLE : View.GONE);
        coordinatorLayout.setVisibility(isShowing ? View.GONE : View.VISIBLE);
        showLoader(false);
    }

    @Override
    public void showError(Throwable e) {
// TODO: 3/19/2019
    }

    @Override
    public void showForecast(ForecastCurrentlyDbModel data) {
        setFields(data, "ToBeChanged");
        Log.d(TAG, "timestamp " + data.getTime());
    }

    public void setFields(ForecastCurrentlyDbModel response, String timezone) {
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
        showEmptyScreen(false);
    }

    public void refreshLastUpdated() {
        String timeStamp = DateFormat
                .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
                .format(new java.util.Date());
        txtLastUpdated.setText(getString(R.string.txt_last_updated, timeStamp));
    }

    /*@Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        swipeRefreshLayout.setEnabled(i == 0);
    }*/
}
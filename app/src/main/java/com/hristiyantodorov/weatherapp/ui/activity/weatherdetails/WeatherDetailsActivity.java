package com.hristiyantodorov.weatherapp.ui.activity.weatherdetails;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.WeatherDetailsPagerAdapter;
import com.hristiyantodorov.weatherapp.model.response.ForecastCurrentlyResponse;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.activity.WeatherDetailsActivityContracts;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.activity.WeatherDetailsActivityPresenter;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import butterknife.BindView;

public class WeatherDetailsActivity extends BaseActivity
        implements WeatherDetailsActivityContracts.View {

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
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.app_bar_basic_forecast)
    AppBarLayout appBarLayout;
    @BindView(R.id.background_weather_image)
    ImageView imgBackground;

    private WeatherDetailsActivityContracts.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        showLoader(true);
        presenter = new WeatherDetailsActivityPresenter(this);
        presenter.downloadForecastFromApi(this);

        WeatherDetailsPagerAdapter weatherDetailsPagerAdapter =
                new WeatherDetailsPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);
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
    }

    @Override
    public void showError(Throwable e) {
        showErrorDialog(this, e);
    }

    @Override
    public void showForecast(ForecastCurrentlyResponse response, String timezone) {
        txtLocationName.setText(timezone);
        txtSummary.setText(response.getSummary());
        txtCurrentTemp.setText(Html.fromHtml(
                WeatherDataFormatterUtil
                        .convertFahrenheitToCelsius(response.getTemperature())
                        + getString(R.string.txt_html_degrees_celsius)
        ));
        txtWindSpeed.setText(getString(
                R.string.txt_current_wind_speed,
                WeatherDataFormatterUtil.convertMphToMs(response.getWindSpeed())
        ));
        imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(response.getIcon()));

        Glide.with(this)
                .load(WeatherIconPickerUtil.pickWeatherBackgroundImage(response.getIcon()))
                .centerCrop()
                .into(imgBackground);

        showLoader(false);
        showEmptyScreen(false);
    }

    public void updateView(ForecastFullResponse response) {
        refreshLastUpdated();
        showForecast(response.getCurrently(), response.getTimezone());
        Log.d(TAG, "updateView: ACTIVITY UPDATED");
    }

    public void refreshLastUpdated() {
        txtLastUpdated.setText(getString(R.string.txt_last_updated, presenter.getTimestamp()));
    }

    @Override
    protected void onDestroy() {
        presenter.clearDisposables();
        super.onDestroy();
    }
}
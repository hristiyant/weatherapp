package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.WeatherDetailsFragmentContracts;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;

import butterknife.BindView;

public class WeatherDetailsFragment extends BaseFragment implements WeatherDetailsFragmentContracts.View {

    @BindView(R.id.txt_forecast_not_available)
    TextView txtForecastNotAvailable;
    @BindView(R.id.txt_temperature)
    TextView txtTemperature;
    @BindView(R.id.txt_apparent_temperature)
    TextView txtApparentTemperature;
    @BindView(R.id.txt_humidity)
    TextView txtHumidity;
    @BindView(R.id.txt_pressure)
    TextView txtPressure;
    @BindView(R.id.txt_wind_speed)
    TextView txtWindSpeed;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_weather_details)
    ConstraintLayout constraintLayout;

    private WeatherDetailsFragmentContracts.Presenter presenter;

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        presenter.loadDataFromDb();

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_weather_details;
    }

    @Override
    public void setPresenter(WeatherDetailsFragmentContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoader(boolean isShowing) {
        progressBar.setVisibility(isShowing ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmptyScreen(boolean isShowing) {
        txtForecastNotAvailable.setVisibility(isShowing ? View.VISIBLE : View.GONE);
        constraintLayout.setVisibility(isShowing ? View.GONE : View.VISIBLE);
        showLoader(false);
    }

    @Override
    public void showError(Throwable e) {
        //TODO: CURRENTLY NOT BEING USED
    }

    @Override
    public void showForecastCurrentlyData(ForecastCurrentlyDbModel data) {
        txtTemperature.setText(getString(R.string.txt_temperature,
                WeatherDataFormatterUtil.convertFahrenheitToCelsius(data.getTemperature())));
        txtApparentTemperature.setText(getString(R.string.txt_apparent_temperature,
                WeatherDataFormatterUtil.convertFahrenheitToCelsius(data.getApparentTemperature())));
        txtHumidity.setText(getString(R.string.txt_humidity,
                WeatherDataFormatterUtil.convertDoubleToPercentage(data.getHumidity())));
        txtPressure.setText(getString(R.string.txt_pressure,
                WeatherDataFormatterUtil.convertRoundedDoubleToString(data.getPressure())));
        txtWindSpeed.setText(getString(R.string.txt_wind_speed,
                WeatherDataFormatterUtil.convertMphToMs(data.getWindSpeed())));
        showEmptyScreen(false);
    }
}
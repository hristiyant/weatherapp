package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingService;
import com.hristiyantodorov.weatherapp.ui.ExceptionHandlerUtil;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;

import butterknife.BindView;

public class WeatherDetailsFragment extends BaseFragment implements DownloadResponse<WeatherData> {

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

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        new NetworkingService().getWeatherDataCurrently(
                WeatherDetailsFragment.this
        );

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_weather_details;
    }

    @Override
    public void onSuccess(WeatherData result) {
        if (result == null) {
            showErrorDialog(getContext(), App.getInstance()
                    .getString(R.string.all_alert_dialog_not_found_message));
        } else {
            txtTemperature.setText(getString(R.string.txt_temperature,
                    WeatherDataFormatterUtil.convertFahrenheitToCelsius(result.getCurrently().getTemperature())));
            txtApparentTemperature.setText(getString(R.string.txt_apparent_temperature,
                    WeatherDataFormatterUtil.convertFahrenheitToCelsius(result.getCurrently().getApparentTemperature())));
            txtHumidity.setText(getString(R.string.txt_humidity,
                    WeatherDataFormatterUtil.convertDoubleToPercentage(result.getCurrently().getHumidity())));
            txtPressure.setText(getString(R.string.txt_pressure,
                    WeatherDataFormatterUtil.convertRoundedDoubleToString(result.getCurrently().getPressure())));
            txtWindSpeed.setText(getString(R.string.txt_wind_speed,
                    WeatherDataFormatterUtil.convertMphToMs(result.getCurrently().getWindSpeed())));
        }
    }

    @Override
    public void onFailure(Exception e) {
        showErrorDialog(getContext(),e.getMessage());
        ExceptionHandlerUtil.logStackTrace(e);
    }

}
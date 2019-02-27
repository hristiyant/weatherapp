package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

import butterknife.BindView;

public class WeatherDetailsFragment extends BaseFragment implements DownloadResponse<WeatherData> {

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment();
    }

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        new NetworkingServiceUtil().getWeatherDataCurrently(WeatherDetailsFragment.this);

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_weather_details;
    }

    @Override
    public void onSuccess(WeatherData result) {
        // TODO: 2/22/2019 Add presenter method for bindging
        txtTemperature.setText("Temperature: " + String.valueOf(result.getCurrently().getTemperature()));
        txtApparentTemperature.setText("Apparent temperature: " + String.valueOf(result.getCurrently().getApparentTemperature()));
        txtHumidity.setText("Humidity: " + String.valueOf(result.getCurrently().getHumidity()));
        txtPressure.setText("Pressure: " + String.valueOf(result.getCurrently().getPressure()));
        txtWindSpeed.setText("Wind speed: " + String.valueOf(result.getCurrently().getWindSpeed()));
    }

    @Override
    public void onFailure(Exception e) {
// TODO: 2/15/2019 Add showErrorDialog()
    }
}
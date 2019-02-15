package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.model.weather.WeatherDataCurrently;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;

import butterknife.BindView;

public class WeatherDetailsFragment extends BaseFragment implements DownloadResponse<WeatherData> {

    @BindView(R.id.txt_timezone)
    TextView txtTimezone;

    private double longitude;
    private double latitude;
    private Geocoder geocoder;
    WeatherDataCurrently weatherDataCurrently;

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        new NetworkingServiceUtil().getWeatherData(WeatherDetailsFragment.this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_weather_details;
    }

    //FIXME Test implementation - get location info
    public void getLongAndLat() {
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onSuccess(WeatherData result) {
        weatherDataCurrently = result.getCurrently();
        txtTimezone.setText(weatherDataCurrently.getIcon());
    }

    @Override
    public void onFailure(Exception e) {
// TODO: 2/15/2019 Add showErrorDialog()
    }
}
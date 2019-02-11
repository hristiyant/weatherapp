package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

import static android.support.constraint.Constraints.TAG;

public class WeatherDetailsFragment extends BaseFragment {

    @BindView(R.id.txt_city_name)
    TextView txtCityName;
    @BindView(R.id.txt_area_name)
    TextView txtAreaName;

    private double longitude;
    private double latitude;
    private Geocoder geocoder;

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        //FIXME Test implementation - get location info
        getLongAndLat();
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            String cityName = addresses.get(0).getLocality();
            String areaName = addresses.get(0).getCountryName();

            Log.d(TAG, "location info: " + cityName + "//" + areaName);

            txtCityName.setText(cityName);
            txtAreaName.setText(areaName);
        }

        return view;
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
}
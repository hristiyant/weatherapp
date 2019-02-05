package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hristiyantodorov.weatherapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;

public class WeatherDetailsFragment extends Fragment {
    private double longitude;
    private double latitude;
    private Geocoder geocoder;

    public WeatherDetailsFragment() {
        // Required empty public constructor
    }

    public static WeatherDetailsFragment newInstance() {
        WeatherDetailsFragment fragment = new WeatherDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);

        ButterKnife.bind(this, view);

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

            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);

            Log.d(TAG, "location info: " + cityName + stateName + countryName);
        }

        return view;
    }

    //FIXME Test implementation - get location info
    public void getLongAndLat() {
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }
}
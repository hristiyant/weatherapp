package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

public class WeatherDetailsFragment extends BaseFragment {

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_weather_details;
    }
}
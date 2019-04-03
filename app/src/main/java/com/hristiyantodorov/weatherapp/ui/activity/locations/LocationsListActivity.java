package com.hristiyantodorov.weatherapp.ui.activity.locations;

import android.os.Bundle;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.locations.LocationsListFragment;

public class LocationsListActivity extends BaseActivity {

    private LocationsListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragment = LocationsListFragment.newInstance();
        commitFragmentTransaction(R.id.content, fragment);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_locations;
    }

}
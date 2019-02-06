package com.hristiyantodorov.weatherapp.ui.activity.locations;

import android.os.Bundle;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.locations.LocationsListFragment;

public class LocationsListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commitFragmentTransaction(R.id.content, LocationsListFragment.newInstance());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_locations;
    }
}
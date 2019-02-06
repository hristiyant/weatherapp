package com.hristiyantodorov.weatherapp.ui.activity.locations;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.locations.LocationsListFragment;

public class LocationsListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, LocationsListFragment.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_locations;
    }
}
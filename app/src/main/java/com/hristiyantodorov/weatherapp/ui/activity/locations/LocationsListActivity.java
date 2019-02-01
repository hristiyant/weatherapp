package com.hristiyantodorov.weatherapp.ui.activity.locations;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.fragment.locations.LocationsListFragment;

public class LocationsListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, LocationsListFragment.newInstance());
        fragmentTransaction.commit();
    }
}
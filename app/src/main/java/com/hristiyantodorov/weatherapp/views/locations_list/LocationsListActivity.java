package com.hristiyantodorov.weatherapp.views.locations_list;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hristiyantodorov.weatherapp.R;

public class LocationsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_list);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, LocationsListFragment.newInstance());
        fragmentTransaction.commit();
    }
}

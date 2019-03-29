package com.hristiyantodorov.weatherapp.ui.activity.locations;

import android.os.Bundle;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListContracts;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListPresenter;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.locations.LocationsListFragment;

public class LocationsListActivity extends BaseActivity {

    private LocationsListFragment fragment;
    private LocationsListContracts.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragment = LocationsListFragment.newInstance();
        presenter = new LocationsListPresenter(fragment, this);
        commitFragmentTransaction(R.id.content, fragment);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_locations;
    }

}
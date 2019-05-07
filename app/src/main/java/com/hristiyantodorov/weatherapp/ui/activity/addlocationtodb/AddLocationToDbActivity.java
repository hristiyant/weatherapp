package com.hristiyantodorov.weatherapp.ui.activity.addlocationtodb;

import android.os.Bundle;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.addlocationtodb.AddLocationToDbFragment;

public class AddLocationToDbActivity extends BaseActivity {

    private AddLocationToDbFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragment = AddLocationToDbFragment.newInstance();
        commitFragmentTransaction(R.id.content, fragment);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_location_to_db;
    }
}

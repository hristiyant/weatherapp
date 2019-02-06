package com.hristiyantodorov.weatherapp.ui.activity.main;

import android.os.Bundle;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.main.MainFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commitFragmentTransaction(R.id.content, MainFragment.newInstance());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}
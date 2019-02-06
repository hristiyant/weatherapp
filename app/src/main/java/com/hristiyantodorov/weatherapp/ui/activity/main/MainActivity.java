package com.hristiyantodorov.weatherapp.ui.activity.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.main.MainFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, MainFragment.newInstance());
        fragmentTransaction.commit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }
}
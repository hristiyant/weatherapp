package com.hristiyantodorov.weatherapp.ui.activity.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.fragment.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, MainFragment.newInstance());
        fragmentTransaction.commit();
    }
}
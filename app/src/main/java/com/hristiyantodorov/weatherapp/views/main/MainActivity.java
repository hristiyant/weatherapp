package com.hristiyantodorov.weatherapp.views.main;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.models.BaseActivity;
import com.hristiyantodorov.weatherapp.views.login.LoginFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, MainFragment.newInstance());
        fragmentTransaction.commit();
    }
}

package com.hristiyantodorov.weatherapp.ui.activity.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.activity.BaseActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.login.LoginFragment;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set day/night theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        //One-time SharedPrefUtil initialization
        SharedPrefUtil.init(App.getInstance().getApplicationContext());

        commitFragmentTransaction(R.id.content, LoginFragment.newInstance());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }
}
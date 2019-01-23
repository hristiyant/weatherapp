package com.hristiyantodorov.weatherapp.ui.activity.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.utils.TimeCalculatorUtil;
import com.hristiyantodorov.weatherapp.ui.fragment.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private TimeCalculatorUtil timeCalculatorUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, LoginFragment.newInstance());
        fragmentTransaction.commit();

    }
}

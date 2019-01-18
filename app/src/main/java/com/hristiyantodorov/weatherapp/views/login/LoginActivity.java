package com.hristiyantodorov.weatherapp.views.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.utils.TimeCalculatorUtil;

public class LoginActivity extends AppCompatActivity {

    private TimeCalculatorUtil timeCalculatorUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Apply appropriate theme based on current time.
        timeCalculatorUtil = new TimeCalculatorUtil();
        if (timeCalculatorUtil.getGreetingBasedOnCurrentTime().equals("Good night!")) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, LoginFragment.newInstance());
        fragmentTransaction.commit();

    }
}

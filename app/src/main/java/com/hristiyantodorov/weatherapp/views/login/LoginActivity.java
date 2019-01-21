package com.hristiyantodorov.weatherapp.views.login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.models.BaseActivity;
import com.hristiyantodorov.weatherapp.utils.TimeCalculatorUtil;

public class LoginActivity extends BaseActivity {

    private TimeCalculatorUtil timeCalculatorUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Apply appropriate theme based on current time.
        timeCalculatorUtil = new TimeCalculatorUtil();
        if (timeCalculatorUtil.getGreetingBasedOnCurrentTime().equals(App.getInstance().getString(R.string.greeting_good_evening))) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, LoginFragment.newInstance());
        fragmentTransaction.commit();

    }
}

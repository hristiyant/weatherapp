package com.hristiyantodorov.weatherapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this, this);
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected void commitFragmentTransaction(int contentResId, Fragment target){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(contentResId, target);
        fragmentTransaction.commit();
    }
}

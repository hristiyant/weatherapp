package com.hristiyantodorov.weatherapp.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.hristiyantodorov.weatherapp.R;

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

    public void showErrorDialog(Context context, String errorMessage) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(R.string.base_fragment_error_dialog_title)
                .setMessage(errorMessage)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

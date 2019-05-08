package com.hristiyantodorov.weatherapp.ui.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hristiyantodorov.weatherapp.R;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    public void showErrorDialog(Context context, Throwable e) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(R.string.base_fragment_error_dialog_title)
                .setMessage(e.getMessage())
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
package com.hristiyantodorov.weatherapp.views.main;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hristiyantodorov.weatherapp.ApiTestActivity;
import com.hristiyantodorov.weatherapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainFragment extends Fragment {


    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);


        MainFragmentPermissionsDispatcher.onButtonPickFromLocationClickWithPermissionCheck(this);

        return view;
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    @OnClick(R.id.image_btn_pick_from_location)
    public void onButtonPickFromLocationClick() {
        MainFragmentPermissionsDispatcher.onButtonPickFromLocationClickWithPermissionCheck(this);
        Intent intent = new Intent(getActivity(), ApiTestActivity.class);
        startActivity(intent);

    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("")
                .setPositiveButton("Allow", (dialog, button) -> request.proceed())
                .setNegativeButton("Deny", (dialog, button) -> request.cancel())
                .show();
    }

    @OnClick(R.id.image_btn_pick_from_list)
    public void onButtonPickFromListClick() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}

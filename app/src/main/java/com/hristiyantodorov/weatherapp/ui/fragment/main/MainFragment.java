package com.hristiyantodorov.weatherapp.ui.fragment.main;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.activity.locations.LocationsListActivity;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainFragment extends Fragment {

    @BindView(R.id.img_btn_pick_location)
    ImageButton imgBtnPickLocation;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.img_btn_pick_location)
    public void onButtonPickFromLocationClick() {
        MainFragmentPermissionsDispatcher.openWeatherDetailsWithPermissionCheck(MainFragment.this);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void openWeatherDetails() {
        Intent intent = new Intent(getActivity(), WeatherDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    void showRationaleForLocation(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.rationale_message)
                .setPositiveButton(R.string.positive_button_text, (dialog, which) -> request.proceed())
                .setNegativeButton(R.string.negative_button_text, (dialog, which) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void onLocationDenied() {
        Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void onNeverAskAgain() {
        Toast.makeText(getContext(), R.string.never_ask_again, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.img_btn_pick_from_list)
    public void onButtonPickFromListClick() {
        Intent intent = new Intent(getActivity(), LocationsListActivity.class);
        startActivity(intent);
    }
}

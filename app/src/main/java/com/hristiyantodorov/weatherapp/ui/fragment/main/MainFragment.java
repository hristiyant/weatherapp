package com.hristiyantodorov.weatherapp.ui.fragment.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.ui.activity.locations.LocationsListActivity;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.CurrentLocationPickerUtil;
import com.hristiyantodorov.weatherapp.util.location.AppLocationService;
import com.hristiyantodorov.weatherapp.util.location.LocationAddress;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainFragment extends BaseFragment implements LocationListener {
    @BindView(R.id.img_btn_pick_location)
    ImageButton imgBtnPickLocation;

    AppLocationService appLocationService;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appLocationService = new AppLocationService(
                Objects.requireNonNull(getContext()));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @OnClick(R.id.img_btn_pick_location)
    public void onButtonPickFromLocationClick() {
        MainFragmentPermissionsDispatcher.openWeatherDetailsWithPermissionCheck(MainFragment.this);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void openWeatherDetails() {
        Location location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);

        //you can hard-code the lat & long if you have issues with getting it
        //remove the below if-condition and use the following couple of lines
        //double latitude = 37.422005;
        //double longitude = -122.084095

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getContext(), new GeocoderHandler());
        } else {
            showSettingsAlert();
        }
        try {
            CurrentLocationPickerUtil.getCurrentLocation(getContext(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(getActivity(), WeatherDetailsActivity.class));
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

    @Override
    public void onLocationChanged(Location location) {
        // TODO: 2/28/2019 CURRENTLY NOT BEING USED
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
// TODO: 2/28/2019 CURRENTLY NOT BEING USED
    }

    @Override
    public void onProviderEnabled(String provider) {
// TODO: 2/28/2019 CURRENTLY NOT BEING USED
    }

    @Override
    public void onProviderDisabled(String provider) {
// TODO: 2/28/2019 CURRENTLY NOT BEING USED
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getContext());
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Objects.requireNonNull(getContext()).startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private static class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Log.d("LOCATION ADDRESS", "handleMessage: " + locationAddress);
        }
    }
}
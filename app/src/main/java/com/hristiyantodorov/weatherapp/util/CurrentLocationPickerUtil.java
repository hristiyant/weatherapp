package com.hristiyantodorov.weatherapp.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.hristiyantodorov.weatherapp.ui.ExceptionHandlerUtil;

import java.io.IOException;

import static android.content.Context.LOCATION_SERVICE;

public class CurrentLocationPickerUtil {

    private static final String TAG = "CLPUtil";
    public static void getCurrentLocation(Context context, LocationListener locationListener) throws IOException {

        LocationManager locationManager;
        Location location = null;
        boolean canGetLocation;
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constants.MIN_TIME, Constants.MIN_DISTANCE, locationListener);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constants.MIN_TIME, Constants.MIN_DISTANCE, locationListener);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }

        } catch (Exception e) {
            ExceptionHandlerUtil.logStackTrace(e);
        }

        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LAT, String.valueOf(location.getLatitude()));
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LON, String.valueOf(location.getLongitude()));
    }
}
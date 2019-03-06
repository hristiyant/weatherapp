package com.hristiyantodorov.weatherapp.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class CurrentLocationPickerUtil {

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
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        /*if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }*/
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                           /* if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }*/
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_NAME, getLocationName(context, location));
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LAT, String.valueOf(location.getLatitude()));
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LON, String.valueOf(location.getLongitude()));
    }

    private static String getLocationName(Context context, Location location) throws IOException {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        if (addresses.size() > 0) {
            return addresses.get(0).getLocality();
        } else {
            // do your stuff
            return "Current Location";
        }
    }
}

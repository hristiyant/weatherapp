package com.hristiyantodorov.weatherapp.service;

import android.location.Location;

public interface LocationService {

    Location getLocationByName(String name);

    Location getLocationById(String id);

    void insertLocation(Location location);
}

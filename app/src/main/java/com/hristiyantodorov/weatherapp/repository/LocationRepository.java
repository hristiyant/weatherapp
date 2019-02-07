package com.hristiyantodorov.weatherapp.repository;

import android.location.Location;

public interface LocationRepository {

    Location getLocationByName(String name);

    Location getLocationById(String id);

    void insertLocation(Location location);
}

package com.hristiyantodorov.weatherapp.model.location;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Arrays;
import java.util.List;

@Entity(tableName = "locations")
public class LocationDbModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo
    private double temperature;
    @ColumnInfo
    private String icon;

    public LocationDbModel(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static List<LocationDbModel> prePopulateLocationsList() {
        return Arrays.asList(
                new LocationDbModel("Tokyo", 35.652832, 139.839478),
                new LocationDbModel("New York", 40.730610, -73.935242),
                new LocationDbModel("Paris", 48.864716, 2.349014),
                new LocationDbModel("London", 51.509865, -0.118092),
                new LocationDbModel("Sydney", -33.865143, 151.209900)
        );
    }

}
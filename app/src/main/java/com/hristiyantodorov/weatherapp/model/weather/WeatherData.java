package com.hristiyantodorov.weatherapp.model.weather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for storing the entire data received from the API
 */
public class WeatherData implements Parcelable {

    private double latitude;
    private double longitude;
    private String timezone;
    private WeatherDataCurrently currently;
    private ForecastDataHourly hourly;
    private ForecastDataDaily daily;

    public WeatherData(){
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public WeatherDataCurrently getCurrently() {
        return currently;
    }

    public void setCurrently(WeatherDataCurrently currently) {
        this.currently = currently;
    }

    public ForecastDataHourly getHourly() {
        return hourly;
    }

    public void setHourly(ForecastDataHourly hourly) {
        this.hourly = hourly;
    }

    public ForecastDataDaily getDaily() {
        return daily;
    }

    public void setDaily(ForecastDataDaily daily) {
        this.daily = daily;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeParcelable(currently, flags);
        dest.writeParcelable(hourly, flags);
        dest.writeParcelable(daily, flags);
    }

    protected WeatherData(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.timezone = in.readString();
        this.currently = in.readParcelable(WeatherDataCurrently.class.getClassLoader());
        this.hourly = in.readParcelable(ForecastDataHourly.class.getClassLoader());
        this.daily = in.readParcelable(ForecastDataDaily.class.getClassLoader());
    }

    public static final Parcelable.Creator<WeatherData> CREATOR =
            new Parcelable.Creator<WeatherData>() {

                @Override
                public WeatherData createFromParcel(Parcel source) {
                    return new WeatherData(source);
                }

                @Override
                public WeatherData[] newArray(int size) {
                    return new WeatherData[size];
                }
            };
}
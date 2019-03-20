package com.hristiyantodorov.weatherapp.model.weather;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherDataDaily implements Parcelable {

    private long time;
    private String summary;
    private String icon;
    private long sunriseTime;
    private long sunsetTime;
    private double humidity;
    private double windSpeed;
    private double temperatureMin;
    private double temperatureMax;
    private long temperatureMinTime;
    private long temperatureMaxTime;

    public WeatherDataDaily(){}

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(long sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public long getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(long sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public long getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public void setTemperatureMinTime(long temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public long getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public void setTemperatureMaxTime(long temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeString(summary);
        dest.writeString(icon);
        dest.writeLong(sunriseTime);
        dest.writeLong(sunsetTime);
        dest.writeDouble(humidity);
        dest.writeDouble(windSpeed);
        dest.writeDouble(temperatureMin);
        dest.writeDouble(temperatureMax);
        dest.writeLong(temperatureMinTime);
        dest.writeLong(temperatureMaxTime);
    }

    protected WeatherDataDaily(Parcel in) {
        this.time = in.readLong();
        this.summary = in.readString();
        this.icon = in.readString();
        this.sunriseTime = in.readLong();
        this.sunsetTime = in.readLong();
        this.humidity = in.readDouble();
        this.windSpeed = in.readDouble();
        this.temperatureMin = in.readDouble();
        this.temperatureMax = in.readDouble();
        this.temperatureMinTime = in.readLong();
        this.temperatureMaxTime = in.readLong();
    }

    public static final Parcelable.Creator<WeatherDataDaily> CREATOR =
            new Parcelable.Creator<WeatherDataDaily>() {

                @Override
                public WeatherDataDaily createFromParcel(Parcel source) {
                    return new WeatherDataDaily(source);
                }

                @Override
                public WeatherDataDaily[] newArray(int size) {
                    return new WeatherDataDaily[size];
                }
            };
}

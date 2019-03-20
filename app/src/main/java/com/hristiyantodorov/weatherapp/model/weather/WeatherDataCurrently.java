package com.hristiyantodorov.weatherapp.model.weather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for storing ONLY the current weather data received from the API
 */
public class WeatherDataCurrently implements Parcelable {

    private long time;
    private String summary;
    private String icon;
    private double temperature;
    private double apparentTemperature;
    private double humidity;
    private double pressure;
    private double windSpeed;

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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
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
        dest.writeDouble(temperature);
        dest.writeDouble(apparentTemperature);
        dest.writeDouble(humidity);
        dest.writeDouble(pressure);
        dest.writeDouble(windSpeed);
    }

    protected WeatherDataCurrently(Parcel in) {
        this.time = in.readLong();
        this.summary = in.readString();
        this.icon = in.readString();
        this.temperature = in.readDouble();
        this.apparentTemperature = in.readDouble();
        this.humidity = in.readDouble();
        this.pressure = in.readDouble();
        this.windSpeed = in.readDouble();
    }

    public static final Parcelable.Creator<WeatherDataCurrently> CREATOR =
            new Parcelable.Creator<WeatherDataCurrently>() {

                @Override
                public WeatherDataCurrently createFromParcel(Parcel source) {
                    return new WeatherDataCurrently(source);
                }

                @Override
                public WeatherDataCurrently[] newArray(int size) {
                    return new WeatherDataCurrently[size];
                }
            };
}
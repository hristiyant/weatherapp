package com.hristiyantodorov.weatherapp.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class ForecastCurrentlyResponse implements Parcelable {

    private Long time;
    private String summary;
    private String icon;
    private Double temperature;
    private Double apparentTemperature;
    private Double humidity;
    private Double pressure;
    private Double windSpeed;

    public ForecastCurrentlyResponse() {
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
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

    protected ForecastCurrentlyResponse(Parcel in) {
        this.time = in.readLong();
        this.summary = in.readString();
        this.icon = in.readString();
        this.temperature = in.readDouble();
        this.apparentTemperature = in.readDouble();
        this.humidity = in.readDouble();
        this.pressure = in.readDouble();
        this.windSpeed = in.readDouble();
    }

    public static final Parcelable.Creator<ForecastCurrentlyResponse> CREATOR =
            new Parcelable.Creator<ForecastCurrentlyResponse>() {

                @Override
                public ForecastCurrentlyResponse createFromParcel(Parcel source) {
                    return new ForecastCurrentlyResponse(source);
                }

                @Override
                public ForecastCurrentlyResponse[] newArray(int size) {
                    return new ForecastCurrentlyResponse[size];
                }
            };
}

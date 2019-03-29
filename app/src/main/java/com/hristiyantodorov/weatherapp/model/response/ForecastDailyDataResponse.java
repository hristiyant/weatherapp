package com.hristiyantodorov.weatherapp.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class ForecastDailyDataResponse implements Parcelable {

    private Long time;
    private String summary;
    private String icon;
    private Long sunriseTime;
    private Long sunsetTime;
    private Double humidity;
    private Double pressure;
    private Double windSpeed;
    private Double temperatureMin;
    private Long temperatureMinTime;
    private Double temperatureMax;
    private Long temperatureMaxTime;

    public ForecastDailyDataResponse() {
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

    public Long getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(Long sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public Long getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(Long sunsetTime) {
        this.sunsetTime = sunsetTime;
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

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Long getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public void setTemperatureMinTime(Long temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Long getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public void setTemperatureMaxTime(Long temperatureMaxTime) {
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
        dest.writeDouble(pressure);
        dest.writeDouble(windSpeed);
        dest.writeDouble(temperatureMin);
        dest.writeDouble(temperatureMax);
        dest.writeLong(temperatureMinTime);
        dest.writeLong(temperatureMaxTime);
    }

    protected ForecastDailyDataResponse(Parcel in) {
        this.time = in.readLong();
        this.summary = in.readString();
        this.icon = in.readString();
        this.sunriseTime = in.readLong();
        this.sunsetTime = in.readLong();
        this.humidity = in.readDouble();
        this.pressure = in.readDouble();
        this.windSpeed = in.readDouble();
        this.temperatureMin = in.readDouble();
        this.temperatureMax = in.readDouble();
        this.temperatureMinTime = in.readLong();
        this.temperatureMaxTime = in.readLong();
    }

    public static final Parcelable.Creator<ForecastDailyDataResponse> CREATOR =
            new Parcelable.Creator<ForecastDailyDataResponse>() {

                @Override
                public ForecastDailyDataResponse createFromParcel(Parcel source) {
                    return new ForecastDailyDataResponse(source);
                }

                @Override
                public ForecastDailyDataResponse[] newArray(int size) {
                    return new ForecastDailyDataResponse[size];
                }
            };
}

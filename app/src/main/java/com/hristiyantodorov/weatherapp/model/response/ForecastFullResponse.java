package com.hristiyantodorov.weatherapp.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class ForecastFullResponse implements Parcelable {

    private Double latitude;
    private Double longitude;
    private String timezone;
    private ForecastCurrentlyResponse currently;
    private ForecastHourlyResponse hourly;
    private ForecastDailyResponse daily;

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

    public ForecastCurrentlyResponse getCurrently() {
        return currently;
    }

    public void setCurrently(ForecastCurrentlyResponse currently) {
        this.currently = currently;
    }

    public ForecastHourlyResponse getHourly() {
        return hourly;
    }

    public void setHourly(ForecastHourlyResponse hourly) {
        this.hourly = hourly;
    }

    public ForecastDailyResponse getDaily() {
        return daily;
    }

    public void setDaily(ForecastDailyResponse daily) {
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

    protected ForecastFullResponse(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.timezone = in.readString();
        this.currently = in.readParcelable(ForecastCurrentlyResponse.class.getClassLoader());
        this.hourly = in.readParcelable(ForecastHourlyResponse.class.getClassLoader());
        this.daily = in.readParcelable(ForecastDailyResponse.class.getClassLoader());
    }

    public static final Parcelable.Creator<ForecastFullResponse> CREATOR =
            new Parcelable.Creator<ForecastFullResponse>() {

                @Override
                public ForecastFullResponse createFromParcel(Parcel source) {
                    return new ForecastFullResponse(source);
                }

                @Override
                public ForecastFullResponse[] newArray(int size) {
                    return new ForecastFullResponse[size];
                }
            };
}
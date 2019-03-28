package com.hristiyantodorov.weatherapp.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ForecastDailyResponse implements Parcelable {

    private String summary;
    private String icon;
    private List<ForecastDailyDataResponse> data = null;

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

    public List<ForecastDailyDataResponse> getData() {
        return data;
    }

    public void setData(List<ForecastDailyDataResponse> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(summary);
        dest.writeString(icon);
        dest.writeTypedList(data);
    }

    protected ForecastDailyResponse(Parcel in) {
        this.summary = in.readString();
        this.icon = in.readString();
        in.readTypedList(data, ForecastDailyDataResponse.CREATOR);
    }

    public static final Parcelable.Creator<ForecastDailyResponse> CREATOR =
            new Parcelable.Creator<ForecastDailyResponse>() {

                @Override
                public ForecastDailyResponse createFromParcel(Parcel source) {
                    return new ForecastDailyResponse(source);
                }

                @Override
                public ForecastDailyResponse[] newArray(int size) {
                    return new ForecastDailyResponse[size];
                }
            };
}

package com.hristiyantodorov.weatherapp.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastHourlyResponse implements Parcelable {

    @SerializedName("summary")
    private String summary;
    @SerializedName("icon")
    private String icon;
    @SerializedName("data")
    private List<ForecastCurrentlyResponse> data = null;

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

    public List<ForecastCurrentlyResponse> getData() {
        return data;
    }

    public void setData(List<ForecastCurrentlyResponse> data) {
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

    protected ForecastHourlyResponse(Parcel in) {
        this.summary = in.readString();
        this.icon = in.readString();
        in.readTypedList(data, ForecastCurrentlyResponse.CREATOR);
    }

    public static final Parcelable.Creator<ForecastHourlyResponse> CREATOR =
            new Parcelable.Creator<ForecastHourlyResponse>() {

                @Override
                public ForecastHourlyResponse createFromParcel(Parcel source) {
                    return new ForecastHourlyResponse(source);
                }

                @Override
                public ForecastHourlyResponse[] newArray(int size) {
                    return new ForecastHourlyResponse[size];
                }
            };
}

package com.hristiyantodorov.weatherapp.model.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ForecastDataDaily extends ForecastData implements Parcelable {

    private List<WeatherDataDaily> data = new ArrayList<>();

    public List<WeatherDataDaily> getData() {
        return data;
    }

    public void setData(List<WeatherDataDaily> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    protected ForecastDataDaily(Parcel in) {
        in.readTypedList(data, WeatherDataDaily.CREATOR);
    }

    public static final Parcelable.Creator<ForecastDataDaily> CREATOR =
            new Parcelable.Creator<ForecastDataDaily>() {

                @Override
                public ForecastDataDaily createFromParcel(Parcel source) {
                    return new ForecastDataDaily(source);
                }

                @Override
                public ForecastDataDaily[] newArray(int size) {
                    return new ForecastDataDaily[size];
                }
            };
}

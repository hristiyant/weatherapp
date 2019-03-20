package com.hristiyantodorov.weatherapp.model.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ForecastDataHourly extends ForecastData implements Parcelable {

    private List<WeatherDataCurrently> data = new ArrayList<>();

    public List<WeatherDataCurrently> getData() {
        return data;
    }

    public void setData(List<WeatherDataCurrently> data) {
        this.data = data;
    }

    public ForecastDataHourly(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    protected ForecastDataHourly(Parcel in) {
        in.readTypedList(data, WeatherDataCurrently.CREATOR);
    }

    public static final Parcelable.Creator<ForecastDataHourly> CREATOR =
            new Parcelable.Creator<ForecastDataHourly>() {

                @Override
                public ForecastDataHourly createFromParcel(Parcel source) {
                    return new ForecastDataHourly(source);
                }

                @Override
                public ForecastDataHourly[] newArray(int size) {
                    return new ForecastDataHourly[size];
                }
            };
}

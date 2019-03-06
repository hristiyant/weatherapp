package com.hristiyantodorov.weatherapp.util.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastHourlyResponse {

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
}

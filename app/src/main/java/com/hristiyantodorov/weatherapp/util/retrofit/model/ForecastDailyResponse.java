package com.hristiyantodorov.weatherapp.util.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ForecastDailyResponse {

    @SerializedName("summary")
    private String summary;
    @SerializedName("icon")
    private String icon;
    @SerializedName("data")
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

}

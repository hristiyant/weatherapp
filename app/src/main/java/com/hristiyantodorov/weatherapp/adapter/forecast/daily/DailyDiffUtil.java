package com.hristiyantodorov.weatherapp.adapter.forecast.daily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;

import java.util.List;

public class DailyDiffUtil extends DiffUtil.Callback {

    private final List<ForecastDailyDataDbModel> oldDailyDataList;
    private final List<ForecastDailyDataDbModel> newDailyDataList;

    public DailyDiffUtil(List<ForecastDailyDataDbModel> oldDailyDataList,
                         List<ForecastDailyDataDbModel> newDailyDataList) {
        this.oldDailyDataList = oldDailyDataList;
        this.newDailyDataList = newDailyDataList;
    }

    @Override
    public int getOldListSize() {
        return oldDailyDataList.size();
    }

    @Override
    public int getNewListSize() {
        return newDailyDataList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        final ForecastDailyDataDbModel oldItem = oldDailyDataList.get(oldItemPosition);
        final ForecastDailyDataDbModel newItem = newDailyDataList.get(newItemPosition);

        return oldItem.getDailyDataId().equals(newItem.getDailyDataId()) &&
                oldItem.getTime().equals(newItem.getTime()) &&
                oldItem.getSummary().equals(newItem.getSummary()) &&
                oldItem.getIcon().equals(newItem.getIcon()) &&
                oldItem.getSunriseTime().equals(newItem.getSunriseTime()) &&
                oldItem.getSunsetTime().equals(newItem.getSunsetTime()) &&
                oldItem.getHumidity().equals(newItem.getHumidity()) &&
                oldItem.getPressure().equals(newItem.getPressure()) &&
                oldItem.getWindSpeed().equals(newItem.getWindSpeed()) &&
                oldItem.getTemperatureMin().equals(newItem.getTemperatureMin()) &&
                oldItem.getTemperatureMinTime().equals(newItem.getTemperatureMinTime()) &&
                oldItem.getTemperatureMax().equals(newItem.getTemperatureMax()) &&
                oldItem.getTemperatureMaxTime().equals(newItem.getTemperatureMaxTime()) &&
                oldItem.getForecastDailyId().equals(newItem.getForecastDailyId());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        final ForecastDailyDataDbModel oldItem = oldDailyDataList.get(oldItemPosition);
        final ForecastDailyDataDbModel newItem = newDailyDataList.get(newItemPosition);

        Bundle diff = new Bundle();
        if (!newItem.getDailyDataId().equals(oldItem.getDailyDataId())) {
            diff.putLong("dailyDataId", newItem.getDailyDataId());
        }
        if (!newItem.getTime().equals(oldItem.getTime())) {
            diff.putString("time", newItem.getTime());
        }
        if (!newItem.getSummary().equals(oldItem.getSummary())) {
            diff.putString("summary", newItem.getSummary());
        }
        if (!newItem.getIcon().equals(oldItem.getIcon())) {
            diff.putString("icon", newItem.getIcon());
        }
        if (!newItem.getSunsetTime().equals(oldItem.getSunriseTime())) {
            diff.putString("sunriseTime", newItem.getSunriseTime());
        }
        if (!newItem.getSunsetTime().equals(oldItem.getSunsetTime())) {
            diff.putString("sunsetTime", newItem.getSunriseTime());
        }
        if (!newItem.getHumidity().equals(oldItem.getHumidity())) {
            diff.putDouble("humidity", newItem.getHumidity());
        }
        if (!newItem.getPressure().equals(oldItem.getPressure())) {
            diff.putDouble("pressure", newItem.getPressure());
        }
        if (!newItem.getWindSpeed().equals(oldItem.getWindSpeed())) {
            diff.putDouble("windSpeed", newItem.getWindSpeed());
        }
        if (!newItem.getTemperatureMin().equals(oldItem.getTemperatureMin())) {
            diff.putDouble("temperatureMin", newItem.getTemperatureMin());
        }
        if (!newItem.getTemperatureMinTime().equals(oldItem.getTemperatureMinTime())) {
            diff.putString("temperatureMinTime", newItem.getTemperatureMinTime());
        }
        if (!newItem.getTemperatureMax().equals(oldItem.getTemperatureMax())) {
            diff.putDouble("temperatureMax", newItem.getTemperatureMax());
        }
        if (!newItem.getTemperatureMaxTime().equals(oldItem.getTemperatureMaxTime())) {
            diff.putString("temperatureMaxTime", newItem.getTemperatureMaxTime());
        }
        if (!newItem.getForecastDailyId().equals(oldItem.getForecastDailyId())) {
            diff.putLong("forecastDailyId", newItem.getForecastDailyId());
        }
        if(diff.size() == 0){
            return null;
        }
        return diff;
    }
}

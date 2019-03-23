package com.hristiyantodorov.weatherapp.adapter.forecast.daily;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;

public class ForecastDailyDiffCallback extends DiffUtil.ItemCallback<ForecastDailyDataDbModel> {

    @Override
    public boolean areItemsTheSame(@NonNull ForecastDailyDataDbModel oldItem, @NonNull ForecastDailyDataDbModel newItem) {
        return oldItem.getDailyDataId() == newItem.getDailyDataId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ForecastDailyDataDbModel oldItem, @NonNull ForecastDailyDataDbModel newItem) {
        return oldItem.equals(newItem);
    }
}

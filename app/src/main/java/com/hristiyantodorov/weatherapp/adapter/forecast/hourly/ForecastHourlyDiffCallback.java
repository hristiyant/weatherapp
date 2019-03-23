package com.hristiyantodorov.weatherapp.adapter.forecast.hourly;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;

public class ForecastHourlyDiffCallback extends DiffUtil.ItemCallback<ForecastCurrentlyDbModel> {

    @Override
    public boolean areItemsTheSame(@NonNull ForecastCurrentlyDbModel oldItem, @NonNull ForecastCurrentlyDbModel newItem) {
        return oldItem.getCurrentlyId() == newItem.getCurrentlyId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ForecastCurrentlyDbModel oldItem, @NonNull ForecastCurrentlyDbModel newItem) {
        return oldItem.equals(newItem);
    }
}

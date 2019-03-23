package com.hristiyantodorov.weatherapp.adapter.locations;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;

public class LocationsListDiffCallback extends DiffUtil.ItemCallback<LocationDbModel> {

    @Override
    public boolean areItemsTheSame(@NonNull LocationDbModel oldItem, @NonNull LocationDbModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull LocationDbModel oldItem, @NonNull LocationDbModel newItem) {
        return oldItem == newItem;
    }
}

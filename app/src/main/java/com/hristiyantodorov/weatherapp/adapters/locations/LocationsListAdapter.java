package com.hristiyantodorov.weatherapp.adapters.locations;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

// TODO: 1/22/2019 This is not implemented.
public class LocationsListAdapter extends RecyclerView.Adapter {

    private List<String> locationsList;

    public LocationsListAdapter() {
        locationsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void addAll(List<String> locations) {
        locationsList.addAll(locations);
    }
}
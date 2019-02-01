package com.hristiyantodorov.weatherapp.adapter.weatherdetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.WeatherDetailsData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherDetailsAdapter extends RecyclerView.Adapter<WeatherDetailsAdapter.WeatherDetailsItemHolder> {
    private List<WeatherDetailsData> itemsList;
    private Context context;

    public WeatherDetailsAdapter(List<WeatherDetailsData> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherDetailsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherDetailsItemHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_weather_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDetailsItemHolder holder, int position) {
        holder.bind(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    static class WeatherDetailsItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_weather_icon)
        ImageView imgWeatherIcon;

        @BindView(R.id.txt_current_temperature)
        TextView txtTemperature;

        @BindView(R.id.txt_conditions)
        TextView txtConditions;

        WeatherDetailsItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(WeatherDetailsData item) {
            txtTemperature.setText(item.getTemperature());
            txtConditions.setText(item.getConditions());
            imgWeatherIcon.setImageResource(item.getDrawable());
        }
    }
}
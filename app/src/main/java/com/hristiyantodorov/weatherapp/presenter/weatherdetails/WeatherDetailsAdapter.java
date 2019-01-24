package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.models.WeatherDetailsData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherDetailsAdapter extends RecyclerView.Adapter {
    List<WeatherDetailsData> demoItems;
    Context context;

    public WeatherDetailsAdapter(List<WeatherDetailsData> demoItems, Context context) {
        this.demoItems = demoItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.weather_details_item, parent, false);
        return new WeatherDetailsItemHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WeatherDetailsData currentItem = demoItems.get(position);
        WeatherDetailsItemHolder weatherDetailsItemHolder = (WeatherDetailsItemHolder) holder;
        weatherDetailsItemHolder.txtTemperature.setText(currentItem.temperature);
        weatherDetailsItemHolder.txtConditions.setText(currentItem.conditions);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Picasso.get().load(R.drawable.animated_button).placeholder(R.drawable.ic_image_black)
                .centerCrop()
                .resize(displayMetrics.widthPixels, displayMetrics.heightPixels / 3)
                .into(weatherDetailsItemHolder.imageViewThumbnail);

    }

    @Override
    public int getItemCount() {
        return demoItems.size();
    }

    public class WeatherDetailsItemHolder extends RecyclerView.ViewHolder {
        ImageView imageViewThumbnail;
        TextView txtTemperature, txtConditions;

        // TODO: 1/24/2019 ButterKnife
        public WeatherDetailsItemHolder(View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById(R.id.img_thumbnail);
            txtTemperature = itemView.findViewById(R.id.txt_temperature);
            txtConditions = itemView.findViewById(R.id.txt_conditions);
        }
    }
}

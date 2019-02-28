package com.hristiyantodorov.weatherapp.adapter.locations;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: 1/22/2019 This is not implemented.
public class LocationsListAdapter extends RecyclerView.Adapter<LocationsListAdapter.LocationsViewHolder> {

    private List<LocationDbModel> locationsList;
    private OnLocationClickListener onLocationClickListener;

    public LocationsListAdapter() {
        locationsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public LocationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_locations_list, viewGroup, false);
        return new LocationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsViewHolder locationsViewHolder, int position) {
        locationsViewHolder.setOnLocationClickListener(this.onLocationClickListener);
        locationsViewHolder.bind(locationsList.get(position));
    }

    @Override
    public int getItemCount() {
        return locationsList.size();
    }

    public void addAll(List<LocationDbModel> locations) {
        locationsList.addAll(locations);
    }

    public void clear() {
        locationsList.clear();
    }

    public void setOnLocationClickListener(OnLocationClickListener onLocationClickListener) {
        this.onLocationClickListener = onLocationClickListener;
    }

    public static class LocationsViewHolder extends RecyclerView.ViewHolder implements DownloadResponse<WeatherData> {

        @BindView(R.id.txt_city_name)
        TextView txtCityName;
        @BindView(R.id.img_weather_icon)
        ImageView imgWeatherIcon;
        @BindView(R.id.txt_current_temperature)
        TextView txtCurrentTemperature;

        private OnLocationClickListener onLocationClickListener;
        private LocationDbModel location;

        public LocationsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(LocationDbModel location) {
            new NetworkingServiceUtil().getWeatherDataCurrently(new DownloadResponse<WeatherData>() {
                @Override
                public void onSuccess(WeatherData result) {
                    txtCurrentTemperature.setText(String.valueOf(result.getCurrently().getTemperature()));
                    imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(result.getCurrently().getIcon()));
                    txtCityName.setText(location.getName());
                }

                @Override
                public void onFailure(Exception e) {

                }
            }, String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            this.location = location;
        }

        @OnClick
        public void onClick() {
            onLocationClickListener.onClick(this.location);
        }

        public void setOnLocationClickListener(OnLocationClickListener onLocationClickListener) {
            this.onLocationClickListener = onLocationClickListener;
        }

        @Override
        public void onSuccess(WeatherData result) {


        }

        @Override
        public void onFailure(Exception e) {

        }
    }

    public interface OnLocationClickListener {
        void onClick(LocationDbModel location);
    }
}
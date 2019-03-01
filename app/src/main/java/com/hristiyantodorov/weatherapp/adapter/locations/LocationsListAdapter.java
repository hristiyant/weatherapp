package com.hristiyantodorov.weatherapp.adapter.locations;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// TODO: 1/22/2019 This is not implemented.
public class LocationsListAdapter
        extends ListAdapter<LocationDbModel, LocationsListAdapter.LocationsViewHolder> {

    private OnLocationClickListener onLocationClickListener;

    public LocationsListAdapter(LocationsListDiffCallback diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public LocationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_locations_list, viewGroup, false);
        return new LocationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsViewHolder viewHolder, int position) {
        viewHolder.bind(getItem(position));
        viewHolder.setOnLocationClickListener(onLocationClickListener);
    }

    public void setOnLocationClickListener(OnLocationClickListener onLocationClickListener) {
        this.onLocationClickListener = onLocationClickListener;
    }

    public static class LocationsViewHolder extends RecyclerView.ViewHolder
            implements DownloadResponse<WeatherData> {

        @BindView(R.id.txt_city_name)
        TextView txtCityName;
        @BindView(R.id.img_weather_icon)
        ImageView imgWeatherIcon;
        @BindView(R.id.txt_current_temperature)
        TextView txtCurrentTemperature;

        private OnLocationClickListener onClickListener;
        private LocationDbModel location;

        LocationsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(LocationDbModel location) {
            new NetworkingServiceUtil().getWeatherDataCurrently(this,
                    String.valueOf(location.getLatitude()),
                    String.valueOf(location.getLongitude())
            );
            this.location = location;
        }

        @OnClick
        void onClick() {
            onClickListener.onClick(this.location);
        }

        void setOnLocationClickListener(OnLocationClickListener onLocationClickListener) {
            onClickListener = onLocationClickListener;
        }

        @Override
        public void onSuccess(WeatherData result) {
            txtCurrentTemperature
                    .setText(App.getInstance().getApplicationContext()
                            .getString(R.string.txt_current_temp_celsius,
                                    WeatherDataFormatterUtil.convertFahrenheitToCelsius(result
                                            .getCurrently()
                                            .getTemperature())));
            imgWeatherIcon
                    .setImageResource(WeatherIconPickerUtil.pickWeatherIcon(result
                            .getCurrently()
                            .getIcon()));
            txtCityName.setText(location.getName());
        }

        @Override
        public void onFailure(Exception e) {
            // TODO: 3/1/2019 CURRENTLY NOT BEING USED
        }
    }

    public interface OnLocationClickListener {
        void onClick(LocationDbModel location);
    }
}
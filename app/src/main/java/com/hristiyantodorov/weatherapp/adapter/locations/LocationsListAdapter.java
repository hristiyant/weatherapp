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
        /*View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_locations_list, viewGroup, false);
        LocationsViewHolder viewHolder = new LocationsViewHolder(view);*/
        return new LocationsViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_locations_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationsViewHolder viewHolder, int position) {
        viewHolder.bind(getItem(position));
        viewHolder.setOnLocationClickListener(onLocationClickListener);
    }

    public void setOnLocationClickListener(OnLocationClickListener onLocationClickListener) {
        this.onLocationClickListener = onLocationClickListener;
    }

    static class LocationsViewHolder extends RecyclerView.ViewHolder {

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
            txtCurrentTemperature
                    .setText(App.getInstance().getApplicationContext()
                            .getString(R.string.txt_current_temp_celsius,
                                    WeatherDataFormatterUtil.
                                            convertFahrenheitToCelsius(location.getTemperature())));
            imgWeatherIcon
                    .setImageResource(WeatherIconPickerUtil.pickWeatherIcon(location.getIcon()));
            txtCityName.setText(location.getName());
            this.location = location;
        }

        @OnClick
        void onClick() {
            onClickListener.onClick(this.location);
        }

        void setOnLocationClickListener(OnLocationClickListener onLocationClickListener) {
            onClickListener = onLocationClickListener;
        }
    }

    public interface OnLocationClickListener {
        void onClick(LocationDbModel location);
    }
}
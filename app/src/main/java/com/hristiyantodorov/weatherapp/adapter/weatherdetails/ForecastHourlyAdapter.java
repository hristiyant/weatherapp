package com.hristiyantodorov.weatherapp.adapter.weatherdetails;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.weather.WeatherDataCurrently;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastHourlyAdapter extends RecyclerView.Adapter<ForecastHourlyAdapter.WeatherDetailsItemHolder> {

    private List<WeatherDataCurrently> itemsList;

    public ForecastHourlyAdapter() {
        itemsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public WeatherDetailsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherDetailsItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast_hourly, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDetailsItemHolder holder, int position) {
        holder.bind(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void addAll(List<WeatherDataCurrently> list) {
        if (!itemsList.isEmpty()) {
            itemsList.clear();
        }
        itemsList.addAll(list);
    }

    public void clear() {
        itemsList.clear();
    }

    static class WeatherDetailsItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_summary)
        TextView txtSummary;
        @BindView(R.id.img_weather_icon)
        ImageView imgWeatherIcon;
        @BindView(R.id.txt_current_temperature)
        TextView txtTemperature;

        WeatherDetailsItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("SetTextI18n")
        void bind(WeatherDataCurrently item) {

            String timeStamp = new SimpleDateFormat(App.getInstance().getString(R.string.all_timestamp_format_hours_minutes))
                    .format(new java.util.Date(item.getTime() * Constants.TIMESTAMP_MILLIS_MULTIPLIER));
            txtTime.setText(timeStamp);
            txtTemperature.setText(Html.fromHtml(WeatherDataFormatterUtil
                    .convertFahrenheitToCelsius(item.getTemperature()) + "<sup>\u00B0c</sup>"));
            if (item.getIcon().equals(App.getInstance().getString(R.string.forecast_hourly_icon_wind_text))) {
                txtSummary.setText(App.getInstance().getString(R.string.forecast_hourly_summary_windy_txt));
            } else {
                txtSummary.setText(item.getSummary());
            }
            imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(item.getIcon()));
        }
    }
}
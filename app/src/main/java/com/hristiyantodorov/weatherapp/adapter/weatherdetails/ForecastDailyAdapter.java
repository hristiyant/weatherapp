package com.hristiyantodorov.weatherapp.adapter.weatherdetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.weather.WeatherDataDaily;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastDailyAdapter extends RecyclerView.Adapter<ForecastDailyAdapter.WeatherDetailsItemHolder> {
    private List<WeatherDataDaily> itemsList;

    public ForecastDailyAdapter() {
        itemsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ForecastDailyAdapter.WeatherDetailsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForecastDailyAdapter.WeatherDetailsItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast_hourly, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDailyAdapter.WeatherDetailsItemHolder holder, int position) {
        holder.bind(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void addAll(List<WeatherDataDaily> list) {
        if (!itemsList.isEmpty()) {
            itemsList.clear();
        }
        itemsList.addAll(list);
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

        void bind(WeatherDataDaily item) {

            String timeStamp = new SimpleDateFormat("EEEE")
                    .format(new java.util.Date(item.getTime() * 1000));
            txtTime.setText(timeStamp.substring(0, 3));

            txtTemperature.setText(
                    Html.fromHtml(WeatherDataFormatterUtil.convertFahrenheitToCelsius(item.getTemperatureMax())
                            + "<sup>\u00B0c</sup>")
            );

            if (item.getIcon().equals("wind")) {
                txtSummary.setText(R.string.txt_summary_windy);
            } else {
                txtSummary.setText(item.getSummary());
            }

            imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(item.getIcon()));
        }
    }
}

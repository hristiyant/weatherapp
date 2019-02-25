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

import java.math.BigDecimal;
import java.math.RoundingMode;
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

            Integer integer =
                    (int) roundDoubleNum(convertFahrenheitToCelsius(item.getTemperatureMax()), 1);
            txtTemperature.setText(Html.fromHtml(String.valueOf(integer) + "<sup>\u00B0c</sup>"));
            txtSummary.setText(item.getSummary());

            int drawableRes = 0;
            switch (item.getIcon()) {
                case "clear-day":
                    drawableRes = R.drawable.ic_clear_day;
                    break;
                case "clear-night":
                    drawableRes = R.drawable.ic_clear_night;
                    break;
                case "rain":
                    drawableRes = R.drawable.ic_rain;
                    break;
                case "snow":
                    drawableRes = R.drawable.ic_snow;
                    break;
                case "sleet":
                    drawableRes = R.drawable.ic_sleet;
                    break;
                case "wind":
                    drawableRes = R.drawable.ic_wind;
                    txtSummary.setText("Windy");
                    break;
                case "fog":
                    drawableRes = R.drawable.ic_fog;
                    break;
                case "cloudy":
                    drawableRes = R.drawable.ic_cloudy;
                    break;
                case "partly-cloudy-day":
                    drawableRes = R.drawable.ic_partly_cloudy_day;
                    break;
                case "partly-cloudy-night":
                    drawableRes = R.drawable.ic_partly_cloudy_night;
                    break;
            }
            imgWeatherIcon.setImageResource(drawableRes);
        }

        private double convertFahrenheitToCelsius(double degreesFahrenheit) {
            return (5.0 / 9.0) * (degreesFahrenheit - 32.0);
        }

        private double roundDoubleNum(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bigDecimal = new BigDecimal(value);
            bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
            return bigDecimal.doubleValue();
        }
    }
}

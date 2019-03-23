package com.hristiyantodorov.weatherapp.adapter.forecast.daily;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForecastDailyAdapter
        extends RecyclerView.Adapter<ForecastDailyAdapter.DailyItemHolder> {

    private List<ForecastDailyDataDbModel> data;
    private OnDailyItemClickListener onDailyItemClickListener;
    private Dialog dialog;

    public ForecastDailyAdapter() {
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public DailyItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast_hourly, parent, false);
        DailyItemHolder viewHolder = new DailyItemHolder(view);

        dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.dialog_daily_item);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.constraintLayoutDaily.setOnClickListener(v -> {
            setUpDialog(getItem(viewHolder.getAdapterPosition()));
            dialog.show();
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyItemHolder holder, int position) {
        holder.bind(getItem(position));
        holder.setOnDailyItemClickListener(onDailyItemClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ForecastDailyDataDbModel getItem(int position) {
        return data.get(position);
    }

    public void clear() {
        data.clear();
    }

    public void addAll(List<ForecastDailyDataDbModel> newData) {
        data.addAll(newData);
    }

    public void setOnDailyItemClickListener(OnDailyItemClickListener onDailyItemclickListener) {
        this.onDailyItemClickListener = onDailyItemclickListener;
    }

    static class DailyItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hourly_item)
        ConstraintLayout constraintLayoutDaily;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_summary)
        TextView txtSummary;
        @BindView(R.id.img_weather_icon)
        ImageView imgWeatherIcon;
        @BindView(R.id.txt_current_temperature)
        TextView txtTemperature;

        private OnDailyItemClickListener onClickListener;
        private ForecastDailyDataDbModel item;

        DailyItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ForecastDailyDataDbModel item) {
            txtTime.setText(item.getTime().substring(0, 3));
            txtTemperature.setText(Html.fromHtml(WeatherDataFormatterUtil
                    .convertFahrenheitToCelsius(item.getTemperatureMax()) + "<sup>\u00B0c</sup>"));
            txtSummary.setText(item.getSummary());
            imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(item.getIcon()));
            this.item = item;
        }

        @OnClick
        void onClick() {
            onClickListener.onClick(this.item);
        }

        void setOnDailyItemClickListener(OnDailyItemClickListener onDailyItemclickListener) {
            onClickListener = onDailyItemclickListener;
        }
    }

    public interface OnDailyItemClickListener {
        void onClick(ForecastDailyDataDbModel item);
    }

    private void setUpDialog(ForecastDailyDataDbModel item) {
        //TODO: Use ButterKnife
        String time = String.valueOf(item.getTime());
        String summary = String.valueOf(item.getSummary());
        String sunriseTime = String.valueOf(item.getSunriseTime());
        String sunsetTime = String.valueOf(item.getSunsetTime());
        String humidity = String.valueOf(item.getHumidity());
        String pressure = String.valueOf(item.getPressure());
        String windSpeed = String.valueOf(item.getWindSpeed());
        String temperatureMin = String.valueOf(item.getTemperatureMin());
        String temperatureMinTime = String.valueOf(item.getTemperatureMinTime());
        String temperatureMax = String.valueOf(item.getTemperatureMax());
        String temperatureMaxTime = String.valueOf(item.getTemperatureMaxTime());
        TextView txtSummary = (TextView) dialog.findViewById(R.id.txt_dialog_summary);
        ImageView imgWeatherIcon = (ImageView) dialog.findViewById(R.id.img_dialog_weather_icon);
        txtSummary.setText(time + "\n" +
                summary + "\n\n" +
                "sunriseTime:\n " + sunriseTime + "\n\n" +
                "sunsetTime:\n " + sunsetTime + "\n\n" +
                "humidity:\n " + humidity + "\n\n" +
                "pressure:\n " + pressure + "\n\n" +
                "windSpeed:\n " + windSpeed + "\n\n" +
                "temperatureMin:\n " + temperatureMin + "\n\n" +
                "temperatureMinTime:\n " + temperatureMinTime + "\n\n" +
                "temperatureMax:\n " + temperatureMax + "\n\n" +
                "temperatureMaxTime:\n " + temperatureMaxTime);
        imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(item.getIcon()));
    }
}

package com.hristiyantodorov.weatherapp.adapter.forecast.hourly;

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

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForecastHourlyAdapter
        extends RecyclerView.Adapter<ForecastHourlyAdapter.HourlyItemHolder> {

    private List<ForecastCurrentlyDbModel> data;
    private OnHourlyItemClickListener onHourlyItemClickListener;
    private Dialog dialog;

    public ForecastHourlyAdapter() {
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public HourlyItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast_hourly, parent, false);
        HourlyItemHolder viewHolder = new HourlyItemHolder(view);

        dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.fragment_dialog_daily_item);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.constraintLayoutHourly.setOnClickListener(v -> {
            setUpDialog(getItem(viewHolder.getAdapterPosition()));
            dialog.show();
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyItemHolder holder, int position) {
        holder.bind(getItem(position));
        holder.setOnHourlyItemClickListener(onHourlyItemClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ForecastCurrentlyDbModel getItem(int position) {
        return data.get(position);
    }

    public void clear() {
        data.clear();
    }

    public void addAll(List<ForecastCurrentlyDbModel> newData) {
        data.addAll(newData);
    }

    public void setOnHourlyItemClickListener(OnHourlyItemClickListener onHourlyItemClickListener) {
        this.onHourlyItemClickListener = onHourlyItemClickListener;
    }

    static class HourlyItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.constraint_layout_item_forecast)
        ConstraintLayout constraintLayoutHourly;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.txt_summary)
        TextView txtSummary;
        @BindView(R.id.img_weather_icon)
        ImageView imgWeatherIcon;
        @BindView(R.id.txt_current_temperature)
        TextView txtTemperature;

        private OnHourlyItemClickListener onClickListener;
        private ForecastCurrentlyDbModel item;

        HourlyItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ForecastCurrentlyDbModel item) {
            txtTime.setText(item.getTime());
            txtTemperature.setText(Html.fromHtml(WeatherDataFormatterUtil.convertFahrenheitToCelsius(item.getTemperature())
                    + App.getInstance().getString(R.string.txt_html_degrees_celsius)));
            if (item.getIcon().equals("wind")) {
                txtSummary.setText(R.string.forecast_hourly_adapter_summary_txt_windy);
            } else {
                txtSummary.setText(item.getSummary());
            }
            imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(item.getIcon()));
            this.item = item;
        }

        @OnClick
        void onClick() {
            onClickListener.onClick(this.item);
        }

        void setOnHourlyItemClickListener(OnHourlyItemClickListener onHourlyitemClickListener) {
            onClickListener = onHourlyitemClickListener;
        }
    }

    public interface OnHourlyItemClickListener {
        void onClick(ForecastCurrentlyDbModel item);
    }

    private void setUpDialog(ForecastCurrentlyDbModel item) {
        String time = String.valueOf(item.getTime());
        String summary = String.valueOf(item.getSummary());
        String temperature = String.valueOf(item.getTemperature());
        String apparentTemperature = String.valueOf(item.getApparentTemperature());
        String humidity = String.valueOf(item.getHumidity());
        String pressure = String.valueOf(item.getPressure());
        String windSpeed = String.valueOf(item.getWindSpeed());

        TextView txtSummary = dialog.findViewById(R.id.txt_dialog_summary);
        ImageView imgWeatherIcon = dialog.findViewById(R.id.img_dialog_weather_icon);
        txtSummary.setText(String.format(
                App.getRes().getString(R.string.dialog_hourly_summary),
                time,
                summary,
                temperature,
                apparentTemperature,
                humidity,
                pressure,
                windSpeed));
        imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(item.getIcon()));
    }
}
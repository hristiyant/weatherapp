package com.hristiyantodorov.weatherapp.adapter.weatherdetails;

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
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.WeatherIconPickerUtil;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastDailyDataResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForecastDailyAdapter
        extends RecyclerView.Adapter<ForecastDailyAdapter.ForecastDailyItemHolder> {

    private List<ForecastDailyDataResponse> itemsList;
    private OnDailyItemClickListener onDailyItemClickListener;
    private Dialog dialog;

    public ForecastDailyAdapter() {
        itemsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ForecastDailyItemHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast_hourly, parent, false);
        ForecastDailyItemHolder viewHolder = new ForecastDailyItemHolder(view);

        dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.dialog_daily_item);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.constraintLayoutDaily.setOnClickListener(v -> {
            ForecastDailyDataResponse item = itemsList.get(viewHolder.getAdapterPosition());
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
            //TextView txtIcon = (TextView) dialog.findViewById(R.id.txt_dialog_icon);
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
            //txtIcon.setText(itemsList.get(viewHolder.getAdapterPosition()).getIcon());
            imgWeatherIcon.setImageResource(WeatherIconPickerUtil.pickWeatherIcon(itemsList.get(viewHolder.getAdapterPosition()).getIcon()));

            dialog.show();
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDailyItemHolder holder,
                                 int position) {
        holder.bind(itemsList.get(position));
        holder.setOnDailyItemClickListener(onDailyItemClickListener);
    }

    public void setOnDailyItemClickListener(OnDailyItemClickListener onDailyItemclickListener) {
        this.onDailyItemClickListener = onDailyItemclickListener;
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void addAll(List<ForecastDailyDataResponse> list) {
        if (!itemsList.isEmpty()) {
            itemsList.clear();
        }
        itemsList.addAll(list);
    }

    static class ForecastDailyItemHolder extends RecyclerView.ViewHolder {

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
        private ForecastDailyDataResponse item;

        ForecastDailyItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ForecastDailyDataResponse item) {

            String timeStamp = new SimpleDateFormat("EEEE")
                    .format(new java.util.Date(item.getTime() * 1000));
            txtTime.setText(timeStamp.substring(0, 3));
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
        void onClick(ForecastDailyDataResponse item);
    }
}

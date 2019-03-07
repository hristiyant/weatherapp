package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.WeatherDetailsContracts;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.WeatherDataFormatterUtil;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

import butterknife.BindView;

public class WeatherDetailsFragment extends BaseFragment implements WeatherDetailsContracts.View {

    public static WeatherDetailsFragment newInstance() {
        return new WeatherDetailsFragment();
    }

    @BindView(R.id.txt_temperature)
    TextView txtTemperature;
    @BindView(R.id.txt_apparent_temperature)
    TextView txtApparentTemperature;
    @BindView(R.id.txt_humidity)
    TextView txtHumidity;
    @BindView(R.id.txt_pressure)
    TextView txtPressure;
    @BindView(R.id.txt_wind_speed)
    TextView txtWindSpeed;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_weather_details)
    ConstraintLayout constraintLayout;

    private WeatherDetailsContracts.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        presenter.requestForecastCurrentlyFromApi();

        //Test forecast object cache
        /*Context context = App.getInstance().getApplicationContext();
        try {
            ForecastFullResponse fullCached = (ForecastFullResponse) InternalStorageCache.readObject(context);
            Log.d("WDFragment", "timezone: " + fullCached.getTimezone());
            Toast.makeText(context,fullCached.getTimezone(),Toast.LENGTH_LONG).show();
            showForecastCurrentlyData(fullCached);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
*/

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_weather_details;
    }

    @Override
    public void showForecastCurrentlyData(ForecastFullResponse response) {
        //txtTemperature.setText("Temperature: " + String.valueOf(result.getCurrently().getTemperature()));
        txtTemperature.setText(getString(R.string.txt_temperature,
                WeatherDataFormatterUtil.convertFahrenheitToCelsius(response.getCurrently().getTemperature())));
//        txtApparentTemperature.setText("Apparent temperature: " + String.valueOf(result.getCurrently().getApparentTemperature()));
        txtApparentTemperature.setText(getString(R.string.txt_apparent_temperature,
                WeatherDataFormatterUtil.convertFahrenheitToCelsius(response.getCurrently().getApparentTemperature())));
//        txtHumidity.setText("Humidity: " + String.valueOf(result.getCurrently().getHumidity()));
        txtHumidity.setText(getString(R.string.txt_humidity,
                WeatherDataFormatterUtil.convertDoubleToPercentage(response.getCurrently().getHumidity())));
//        txtPressure.setText("Pressure: " + String.valueOf(result.getCurrently().getPressure()));
        txtPressure.setText(getString(R.string.txt_pressure,
                WeatherDataFormatterUtil.convertRoundedDoubleToString(response.getCurrently().getPressure())));
//        txtWindSpeed.setText("Wind speed: " + String.valueOf(result.getCurrently().getWindSpeed()));
        txtWindSpeed.setText(getString(R.string.txt_wind_speed,
                WeatherDataFormatterUtil.convertMphToMs(response.getCurrently().getWindSpeed())));
        progressBar.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(WeatherDetailsContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading() {
        //TODO: CURRENTLY NOT BEING USED
    }

    @Override
    public void hideLoading() {
        //TODO: CURRENTLY NOT BEING USED
    }
}
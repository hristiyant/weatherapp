package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.ForecastDailyAdapter;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.networking.service.NetworkingService;
import com.hristiyantodorov.weatherapp.ui.ExceptionHandlerUtil;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import java.util.Objects;

import butterknife.BindView;

public class ForecastDailyFragment extends BaseFragment implements DownloadResponse<WeatherData>,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    private ForecastDailyAdapter dailyAdapter;

    public static ForecastDailyFragment newInstance() {
        return new ForecastDailyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        dailyAdapter = new ForecastDailyAdapter();

        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewForecast.setAdapter(dailyAdapter);
        recyclerViewForecast.addItemDecoration(new DividerItemDecoration(
                App.getInstance().getApplicationContext(), DividerItemDecoration.VERTICAL
        ));

        new NetworkingService().getWeatherDataDaily(
                this,
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null)
        );

        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_forecast;
    }

    @Override
    public void onSuccess(WeatherData result) {
        dailyAdapter.addAll(result.getDaily().getData());
        dailyAdapter.notifyDataSetChanged();

        ((WeatherDetailsActivity) Objects.requireNonNull(getActivity())).refreshLastUpdated();
    }

    @Override
    public void onFailure(Exception e) {
        ExceptionHandlerUtil.throwException(e);
        ExceptionHandlerUtil.logStackTrace(e);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new NetworkingService().getWeatherDataDaily(this,
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null)
        );
        swipeRefreshLayout.setRefreshing(false);
    }
}
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
import com.hristiyantodorov.weatherapp.networking.service.NetworkingServiceUtil;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

import java.util.Objects;

import butterknife.BindView;

public class ForecastDailyFragment extends BaseFragment implements DownloadResponse<WeatherData>,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    ForecastDailyAdapter dailyAdapter;

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

        new NetworkingServiceUtil().getWeatherDataDaily(this);

        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    /*@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dailyAdapter = new ForecastDailyAdapter();

        *//*recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewForecast.setAdapter(dailyAdapter);
        recyclerViewForecast.addItemDecoration(new DividerItemDecoration(
                App.getInstance().getApplicationContext(), DividerItemDecoration.VERTICAL
        ));*//*

        new NetworkingServiceUtil().getWeatherDataDaily(this);

        swipeRefreshLayout.setOnRefreshListener(this);
    }
*/
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

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new NetworkingServiceUtil().getWeatherDataDaily(this);
        swipeRefreshLayout.setRefreshing(false);
    }
}
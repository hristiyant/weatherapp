package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.ForecastHourlyAdapter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecasthourly.ForecastHourlyContracts;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastCurrentlyResponse;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class ForecastHourlyFragment extends BaseFragment implements ForecastHourlyContracts.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ForecastHourlyAdapter hourlyAdapter;
    private ForecastHourlyContracts.Presenter presenter;

    public static ForecastHourlyFragment newInstance() {
        return new ForecastHourlyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        hourlyAdapter = new ForecastHourlyAdapter();

        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewForecast.setAdapter(hourlyAdapter);
        recyclerViewForecast.addItemDecoration(new DividerItemDecoration(
                App.getInstance().getApplicationContext(), DividerItemDecoration.VERTICAL
        ));

        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.loadForecastHourlyData();
        ((WeatherDetailsActivity) Objects.requireNonNull(getActivity())).refreshLastUpdated();

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_forecast;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadForecastHourlyData();
        ((WeatherDetailsActivity) Objects.requireNonNull(getActivity())).refreshLastUpdated();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.loadForecastHourlyData();
        ((WeatherDetailsActivity) Objects.requireNonNull(getActivity())).refreshLastUpdated();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(ForecastHourlyContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showForecastHourlyData(List<ForecastCurrentlyResponse> hourlyData) {
        hourlyAdapter.clear();
        hourlyAdapter.addAll(hourlyData);
        hourlyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoader(boolean isShowing) {
        progressBar.setVisibility(isShowing ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        //TODO: NOT CURRENTLY BEING USED
    }

}
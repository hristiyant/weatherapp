package com.hristiyantodorov.weatherapp.ui.fragment.forecasthourly;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.forecast.hourly.ForecastHourlyAdapter;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.presenter.forecasthourly.ForecastHourlyContracts;
import com.hristiyantodorov.weatherapp.presenter.forecasthourly.ForecastHourlyPresenter;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class ForecastHourlyFragment extends BaseFragment
        implements ForecastHourlyContracts.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.txt_forecast_not_available)
    TextView txtForecastNotAvailable;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;

    private ForecastHourlyAdapter hourlyAdapter;
    private ForecastHourlyContracts.Presenter presenter;

    public static ForecastHourlyFragment newInstance() {
        ForecastHourlyFragment fragment = new ForecastHourlyFragment();
        new ForecastHourlyPresenter(fragment);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hourlyAdapter = new ForecastHourlyAdapter();

        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewForecast.setAdapter(hourlyAdapter);
        recyclerViewForecast.addItemDecoration(new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL
        ));

        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.loadDataFromDb();
    }

    @Override
    public void setPresenter(ForecastHourlyContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_forecast;
    }

    @Override
    public void showLoader(boolean isShowing) {
        progressBar.setVisibility(isShowing ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmptyScreen(boolean isShowing) {
        txtForecastNotAvailable.setVisibility(isShowing ? View.VISIBLE : View.GONE);
        recyclerViewForecast.setVisibility(isShowing ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showError(Throwable e) {
        showErrorDialog(e);
    }

    @Override
    public void showForecast(List<ForecastCurrentlyDbModel> hourlyData) {
        hourlyAdapter.clear();
        hourlyAdapter.addAll(hourlyData);
        hourlyAdapter.notifyDataSetChanged();
        showLoader(false);
        showEmptyScreen(false);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateActivity(ForecastFullResponse response) {
        ((WeatherDetailsActivity) Objects.requireNonNull(getActivity())).updateView(response);
    }

    @Override
    public void onRefresh() {
        presenter.updateForecastHourlyDataFromApi();
    }

    @Override
    public void onDestroy() {
        presenter.clearDisposables();
        super.onDestroy();
    }
}
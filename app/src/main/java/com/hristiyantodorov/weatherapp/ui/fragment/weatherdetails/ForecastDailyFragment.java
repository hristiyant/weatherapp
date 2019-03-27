package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails;

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
import com.hristiyantodorov.weatherapp.adapter.forecast.daily.ForecastDailyAdapter;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily.ForecastDailyContracts;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily.ForecastDailyPresenter;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class ForecastDailyFragment extends BaseFragment
        implements ForecastDailyContracts.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.txt_forecast_not_available)
    TextView txtForecastNotAvailable;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;

    private ForecastDailyAdapter dailyAdapter;
    private ForecastDailyContracts.Presenter presenter;

    public static ForecastDailyFragment newInstance() {
        ForecastDailyFragment fragment = new ForecastDailyFragment();
        new ForecastDailyPresenter(fragment);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dailyAdapter = new ForecastDailyAdapter();

        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewForecast.setAdapter(dailyAdapter);
        recyclerViewForecast.addItemDecoration(new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL
        ));

        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.loadDataFromDb(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe(this);
    }

    @Override
    public void setPresenter(ForecastDailyContracts.Presenter presenter) {
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
        showErrorDialog(getContext(), e);
    }

    @Override
    public void showForecast(List<ForecastDailyDataDbModel> dailyData) {
        dailyAdapter.clear();
        dailyAdapter.addAll(dailyData);
        dailyAdapter.notifyDataSetChanged();
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
        presenter.updateForecastDailyDataFromApi(getContext());
    }

    @Override
    public void onDestroy() {
        presenter.clearDisposables();
        super.onDestroy();
    }
}
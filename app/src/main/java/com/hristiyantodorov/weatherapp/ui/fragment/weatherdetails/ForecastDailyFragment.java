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
import com.hristiyantodorov.weatherapp.adapter.weatherdetails.ForecastDailyAdapter;
import com.hristiyantodorov.weatherapp.presenter.weatherdetails.forecastdaily.ForecastDailyContracts;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastDailyDataResponse;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class ForecastDailyFragment extends BaseFragment implements ForecastDailyContracts.View,
        SwipeRefreshLayout.OnRefreshListener, ForecastDailyAdapter.OnDailyItemClickListener {

    @BindView(R.id.recycler_view_forecast)
    RecyclerView recyclerViewForecast;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ForecastDailyAdapter dailyAdapter;
    private ForecastDailyContracts.Presenter presenter;

    public static ForecastDailyFragment newInstance() {
        return new ForecastDailyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        dailyAdapter = new ForecastDailyAdapter();
        dailyAdapter.setOnDailyItemClickListener(this);

        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewForecast.setAdapter(dailyAdapter);
        recyclerViewForecast.addItemDecoration(new DividerItemDecoration(
                App.getInstance().getApplicationContext(), DividerItemDecoration.VERTICAL
        ));

        /*presenter.requestForecastDailyDataFromApi(
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null)
        );*/
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clearResources();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_forecast;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        presenter.requestForecastDailyDataFromApi(
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT, null),
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON, null)
        );

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showForecastDailyData(List<ForecastDailyDataResponse> result) {
        dailyAdapter.addAll(result);
        dailyAdapter.notifyDataSetChanged();
        ((WeatherDetailsActivity) Objects.requireNonNull(getActivity())).refreshLastUpdated();
    }

    @Override
    public void showEmptyForecast() {

    }

    @Override
    public void setPresenter(ForecastDailyContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoader(boolean isShowing) {
        progressBar.setVisibility(isShowing ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmptyScreen(boolean isShowing) {
        //TODO: IMPLEMENT !!!!
    }

    @Override
    public void showError(Throwable e) {
        //TODO:CURRENTLY NOT BEING USED
    }

    @Override
    public void onClick(ForecastDailyDataResponse item) {

        //dialog.show();
    }
}
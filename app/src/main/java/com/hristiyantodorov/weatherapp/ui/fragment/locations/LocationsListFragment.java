package com.hristiyantodorov.weatherapp.ui.fragment.locations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.locations.LocationsListAdapter;
import com.hristiyantodorov.weatherapp.adapter.locations.LocationsListDiffCallback;
import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListContracts;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.view.DividerItemDecoration;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class LocationsListFragment extends BaseFragment
        implements LocationsListContracts.View, LocationsListAdapter.OnLocationClickListener {

    private static final String TAG = "LLF";

    @BindView(R.id.edt_filter)
    EditText edtFilter;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_locations)
    RecyclerView recyclerViewLocations;
    @BindView(R.id.txt_no_results_found)
    TextView txtNoResultsFound;

    private LocationsListAdapter locationsListAdapter;
    private LocationsListContracts.Presenter presenter;
    private Timer timer;

    public static LocationsListFragment newInstance() {
        return new LocationsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        locationsListAdapter = new LocationsListAdapter(new LocationsListDiffCallback());
        locationsListAdapter.setOnLocationClickListener(this);

        edtFilter.addTextChangedListener(filterTextWatcher);
        recyclerViewLocations.setAdapter(locationsListAdapter);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLocations.addItemDecoration(new DividerItemDecoration(
                Objects.requireNonNull(getContext())
        ));
        showLoader(true);
        presenter.loadDbData();

        return view;
    }

    @Override
    public void setPresenter(LocationsListContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_locations_list;
    }

    @Override
    public void showLoader(boolean isShowing) {
        progressBar.setVisibility(isShowing ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmptyScreen(boolean isShowing) {
        txtNoResultsFound.setVisibility(isShowing ? View.VISIBLE : View.GONE);
        recyclerViewLocations.setVisibility(isShowing ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showError(Throwable e) {
        showErrorDialog(getContext(), e);
    }

    @Override
    public void showLocations(List<LocationDbModel> locations) {
        locationsListAdapter.submitList(locations);
        showLoader(false);
        showEmptyScreen(false);
    }

    @Override
    public void onClick(LocationDbModel location) {
        presenter.selectLocation(
                String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()),
                getContext());
    }

    @Override
    public void openWeatherDetailsActivity() {
        startActivity(new Intent(getContext(), WeatherDetailsActivity.class));
    }

    @Override
    public void onDestroy() {
        presenter.clearDisposables();
        super.onDestroy();
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable arg0) {
            txtNoResultsFound.setVisibility(View.GONE);
            showLoader(true);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                        String pattern = edtFilter.getText().toString().toLowerCase();
                        presenter.filterLocations(pattern, getContext());
                    });
                }
            }, Constants.DEBOUNCE_DELAY_MILLIS);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO: 2/27/2019 NOT USED
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (timer != null) {
                timer.cancel();
            }
        }
    };
}
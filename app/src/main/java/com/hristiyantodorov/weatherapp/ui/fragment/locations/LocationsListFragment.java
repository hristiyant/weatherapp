package com.hristiyantodorov.weatherapp.ui.fragment.locations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.locations.LocationsListAdapter;
import com.hristiyantodorov.weatherapp.adapter.locations.LocationsListDiffCallback;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListContracts;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        showLoader(true);

        locationsListAdapter = new LocationsListAdapter(new LocationsListDiffCallback());
        locationsListAdapter.setOnLocationClickListener(this);

        edtFilter.addTextChangedListener(filterTextWatcher);
        recyclerViewLocations.setAdapter(locationsListAdapter);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLocations.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));
        getLocationsFromDatabase();

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_locations_list;
    }

    @Override
    public void showLocations(List<LocationDbModel> locations) {
        locationsListAdapter.submitList(locations);
        Log.d(TAG, "showLocations ");
    }

    @Override
    public void getLocationsFromDatabase() {
        presenter.loadLocationsFromDatabase();
        Log.d(TAG, "getLocationsFromDatabase: ");
    }

    @Override
    public void setPresenter(LocationsListContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoader(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        if (isAdded()) {
            showErrorDialog(getContext(), e.getMessage());
        }
    }

    @Override
    public void onClick(LocationDbModel location) {
        presenter.selectLocation(location);
        Log.d(TAG, "onClick: ");
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
                        presenter.filterLocations(pattern);
                        Log.d(TAG, "run: presenter.filterLocations");
                    });
                    InputMethodManager in = (InputMethodManager) App.getInstance().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(edtFilter.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
                Log.d(TAG, "onTextChanged: resetting timer");
            }
        }
    };

    @Override
    public void showLocationWeatherDetails(LocationDbModel selectedLocation) {
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_NAME, selectedLocation.getName());
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LAT, String.valueOf(selectedLocation.getLatitude()));
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LON, String.valueOf(selectedLocation.getLongitude()));
        startActivity(new Intent(getContext(), WeatherDetailsActivity.class));
    }

}
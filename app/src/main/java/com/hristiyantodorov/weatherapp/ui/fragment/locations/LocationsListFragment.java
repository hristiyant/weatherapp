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
import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListContracts;
import com.hristiyantodorov.weatherapp.ui.activity.weatherdetails.WeatherDetailsActivity;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.view.DividerItemDecoration;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import butterknife.BindView;

public class LocationsListFragment extends BaseFragment implements LocationsListContracts.View, LocationsListAdapter.OnLocationClickListener {

    @BindView(R.id.edt_filter)
    EditText edtFilter;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.rv_locations)
    RecyclerView recyclerViewLocations;
    @BindView(R.id.txt_no_results_found)
    TextView txtNoResultsFound;

    private LocationsListAdapter locationsAdapter;
    private LocationsListContracts.Presenter presenter;
    private Timer timer;

    public static LocationsListFragment newInstance() {
        return new LocationsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerViewLocations.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        locationsAdapter = new LocationsListAdapter();
        locationsAdapter.setOnLocationClickListener(this);

        edtFilter.addTextChangedListener(filterTextWatcher);
        recyclerViewLocations.setAdapter(locationsAdapter);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLocations.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));
        loadDefaultCities();

        return view;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_locations_list;
    }

    /*@Override
    public void showLoader(int visibility) {

    }*/

    @Override
    public void showLocations(List<LocationDbModel> locations) {
        locationsAdapter.clear();
        if (locations.isEmpty()) {
            recyclerViewLocations.setVisibility(View.GONE);
            txtNoResultsFound.setVisibility(View.VISIBLE);
        } else {
            locationsAdapter.addAll(locations);
        }
        locationsAdapter.notifyDataSetChanged();
        hideLoading();
        Log.d("SUCC", "Success ");
    }

    @Override
    public void showDefaultLocationsList() {
        // TODO: 1/22/2019 Implement
    }

    @Override
    public void showError(Throwable e) {
        // TODO: 1/22/2019 Implement
    }

    @Override
    public void setPresenter(LocationsListContracts.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(LocationDbModel location) {
        presenter.selectLocation(location);
        Log.d("LLF", "onClick: ");
    }

    @Override
    public void showLocationWeatherDetails(LocationDbModel selectedLocation) {
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_NAME, selectedLocation.getName());
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LAT, String.valueOf(selectedLocation.getLatitude()));
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LON, String.valueOf(selectedLocation.getLongitude()));
        startActivity(new Intent(getContext(), WeatherDetailsActivity.class));
    }

    @Override
    public void showLoading() {
        recyclerViewLocations.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerViewLocations.setVisibility(View.VISIBLE);
        edtFilter.setEnabled(true);
    }

    private void loadDefaultCities() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<LocationDbModel> defaultLocationsList = PersistenceDatabase.getAppDatabase(App.getInstance().getApplicationContext()).locationDao().getAllLocations();
            locationsAdapter.addAll(defaultLocationsList);
        });
        hideLoading();
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable arg0) {
            // user typed: start the timer
            txtNoResultsFound.setVisibility(View.GONE);
            showLoading();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // do your actual work here
                    getActivity().runOnUiThread(() -> {
                        String pattern = edtFilter.getText().toString().toLowerCase();
                        presenter.filterLocations(pattern);
                        hideLoading();
                    });

                    // hide keyboard when recyclerView is visible
                    InputMethodManager in = (InputMethodManager) App.getInstance().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(edtFilter.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }, 300); // 300ms delay before the timer executes the "run" method from TimerTask
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO: 2/27/2019 NOT USED
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // user is typing: reset already started timer (if existing)
            if (timer != null) {
                timer.cancel();
            }
        }
    };
}
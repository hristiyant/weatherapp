package com.hristiyantodorov.weatherapp.ui.fragment.locations;

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
import com.hristiyantodorov.weatherapp.util.DisposableManagerUtil;
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
    @BindView(R.id.rv_locations)
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

        locationsListAdapter = new LocationsListAdapter(new LocationsListDiffCallback());
        locationsListAdapter.setOnLocationClickListener(this);

        edtFilter.addTextChangedListener(filterTextWatcher);
        recyclerViewLocations.setAdapter(locationsListAdapter);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLocations.addItemDecoration(new DividerItemDecoration(
                Objects.requireNonNull(getContext())
        ));
        presenter.loadDbData();

        return view;
    }

    @Override
    public void onDestroy() {
        DisposableManagerUtil.dispose();
        super.onDestroy();
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
//        recyclerViewLocations.setVisibility(isShowing ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showError(Throwable e) {
        // TODO: 1/22/2019 CURRENTLY NOT BEING USED
    }

    @Override
    public void showLocations(List<LocationDbModel> locations) {
        locationsListAdapter.submitList(locations);
        locationsListAdapter.notifyDataSetChanged();
//        recyclerViewLocations.setVisibility(View.VISIBLE);
        showLoader(false);
        showEmptyScreen(false);
    }

    @Override
    public void getLocationsFromDatabase() {
        showLoader(true);

        //TODO: check for info in the db and then get from API
//        presenter.downloadApiDataForDbModels(getContext());
    }

    @Override
    public void getBasicForecastInfo(List<LocationDbModel> locations) {
        for (LocationDbModel location : locations) {
//            presenter.getBasicForecastInfo(location);
        }
        //TODO: GET DATA FROM DB AND PASS TO VIEW TO SHOW
        presenter.loadDbData();
    }

    @Override
    public void updateApiInfo(LocationDbModel location) {
        presenter.updateLocationDbInfo(location);
    }

    @Override
    public void onClick(LocationDbModel location) {
//        presenter.selectLocation(location);
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LAT, String.valueOf(location.getLatitude()));
        SharedPrefUtil.write(Constants.SHARED_PREF_LOCATION_LON, String.valueOf(location.getLongitude()));
        startActivity(new Intent(getContext(), WeatherDetailsActivity.class));
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable arg0) {
            // user typed: start the timer
            txtNoResultsFound.setVisibility(View.GONE);
            showLoader(true);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                        String pattern = edtFilter.getText().toString().toLowerCase();
                        presenter.filterLocations(pattern, getContext());
                        Log.d(TAG, "run: presenter.filterLocations");
                    });
                    /*// hide keyboard when recyclerView is visible
                    InputMethodManager in = (InputMethodManager) App.getInstance().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(edtFilter.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/
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
                Log.d(TAG, "onTextChanged: resetting timer");
            }
        }
    };
}
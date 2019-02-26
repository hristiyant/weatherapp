package com.hristiyantodorov.weatherapp.ui.fragment.locations;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.locations.LocationsListAdapter;
import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListContracts;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;
import com.hristiyantodorov.weatherapp.view.DividerItemDecoration;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnTextChanged;

public class LocationsListFragment extends BaseFragment implements LocationsListContracts.View, LocationsListAdapter.OnLocationClickListener {

    @BindView(R.id.edt_filter)
    EditText edtFilter;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.rv_locations)
    RecyclerView recyclerViewLocations;

    private LocationsListAdapter locationsAdapter;
    private LocationsListContracts.Presenter presenter;

    public static LocationsListFragment newInstance() {
        return new LocationsListFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationsAdapter = new LocationsListAdapter();
        locationsAdapter.setOnLocationClickListener(this);

        recyclerViewLocations.setAdapter(locationsAdapter);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewLocations.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));
        loadDefaultCities();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe(this);
        presenter.loadLocations();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_locations_list;
    }

    @Override
    public void showLoader(boolean isShowing) {
        Toast.makeText(getActivity(), "LOADING....", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLocations(List<LocationDbModel> locations) {
        this.locationsAdapter.clear();
        this.locationsAdapter.addAll(locations);
        this.locationsAdapter.notifyDataSetChanged();
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
    public void showLocationWeatherDetails() {
        // TODO: 1/22/2019 Implement
    }

    private void loadDefaultCities() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<LocationDbModel> defaultLocationsList = PersistenceDatabase.getAppDatabase(getActivity()).locationDao().getAllLocations();
            locationsAdapter.addAll(defaultLocationsList);
        });
    }

    @Override
    public void onClick(LocationDbModel location) {
        presenter.selectLocation(location);
        Log.d("LLF", "onClick: " + location.toString());
    }

    @OnTextChanged(R.id.edt_filter)
    public void onTextChanged() {
        String pattern = edtFilter.getText().toString();
        this.presenter.filterLocations(pattern);
    }
}
package com.hristiyantodorov.weatherapp.ui.fragment.locations;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapters.locations.LocationsListAdapter;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListContracts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;


public class LocationsListFragment extends Fragment implements LocationsListContracts.View {

    @BindView(R.id.edt_filter)
    EditText filter;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @BindView(R.id.rv_locations)
    RecyclerView locationsList;

    private LocationsListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private LocationsListContracts.Presenter presenter;

    public LocationsListFragment() {
        // Required empty public constructor
    }

    public static LocationsListFragment newInstance() {
        return new LocationsListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_locations_list, container, false);

        ButterKnife.bind(this, view);

        locationsList.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        locationsList.setLayoutManager(gridLayoutManager);


        return view;
    }


    @OnTextChanged(R.id.edt_filter)
    public void onTextChanged() {
        String pattern = filter.getText().toString();
        //TODO presenter - add filter method and call here
    }

    @Override
    public void showLoader(boolean isShowing) {
        // TODO: 1/22/2019 Implement
    }

    @Override
    public void showLocations(List<String> locations) {
        // TODO: 1/22/2019 Implement
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
        // TODO: 1/22/2019 Implement
    }

    @Override
    public void showLocationWeatherDetails() {
        // TODO: 1/22/2019 Implement
    }
}

package com.hristiyantodorov.weatherapp.ui.fragment.locations;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.locations.LocationsListAdapter;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListContracts;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;

import static com.hristiyantodorov.weatherapp.util.Constants.LOCATIONS_LIST_GRID_LAYOUT_MANAGER_SPAN_COUNT;

public class LocationsListFragment extends BaseFragment implements LocationsListContracts.View {

    @BindView(R.id.edt_filter)
    EditText edtFilter;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.rv_locations)
    RecyclerView recyclerViewLocations;

    private LocationsListAdapter locationsAdapter;
    private GridLayoutManager gridLayoutManager;
    private LocationsListContracts.Presenter presenter;

    public static LocationsListFragment newInstance() {
        return new LocationsListFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewLocations.setAdapter(locationsAdapter);
        gridLayoutManager = new GridLayoutManager(getContext(), LOCATIONS_LIST_GRID_LAYOUT_MANAGER_SPAN_COUNT);
        recyclerViewLocations.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_locations_list;
    }

    @OnTextChanged(R.id.edt_filter)
    public void onTextChanged() {
        String pattern = edtFilter.getText().toString();
        //TODO presenter - add edtFilter method and call here
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
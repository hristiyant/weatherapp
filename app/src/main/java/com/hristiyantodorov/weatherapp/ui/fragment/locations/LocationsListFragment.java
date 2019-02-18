package com.hristiyantodorov.weatherapp.ui.fragment.locations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.adapter.locations.LocationsListAdapter;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.locations.LocationsListContracts;
import com.hristiyantodorov.weatherapp.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;

import static com.hristiyantodorov.weatherapp.util.Constants.LOCATIONS_LIST_GRID_LAYOUT_MANAGER_SPAN_COUNT;

public class LocationsListFragment extends BaseFragment implements LocationsListContracts.View {

    @BindView(R.id.edt_filter)
    EditText edtFilter;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_locations)
    RecyclerView recyclerViewLocations;

    private LocationsListAdapter locationsAdapter;
    private GridLayoutManager gridLayoutManager;
    private LocationsListContracts.Presenter presenter;

    public static LocationsListFragment newInstance() {
        return new LocationsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerViewLocations.setAdapter(new LocationsListAdapter(feedItems(), getContext()));
        gridLayoutManager = new GridLayoutManager(getContext(), LOCATIONS_LIST_GRID_LAYOUT_MANAGER_SPAN_COUNT);
        recyclerViewLocations.setLayoutManager(gridLayoutManager);

        return view;
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
        progressBar.setVisibility(isShowing ? View.GONE : View.VISIBLE);
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
        if (isAdded()) {
            showErrorDialog(getContext(), e.getMessage());
        }
    }

    @Override
    public void setPresenter(LocationsListContracts.Presenter presenter) {
        // TODO: 1/22/2019 Implement
    }

    @Override
    public void showLocationWeatherDetails() {
        // TODO: 1/22/2019 Implement
    }

    // TODO: 2/13/2019 Add dummy data for test implementation
    private List<LocationDbModel> feedItems() {
        return new ArrayList<>(Arrays.asList(
                new LocationDbModel("Sydney", -33.838314, 150.999260,
                        "https://s2.thingpic.com/images/AC/ZP5RrCrtTJ83eWDuK9SmUSaf.jpeg"),
                new LocationDbModel("New York", 40.730610, -73.935242,
                        "https://www.housingwire.com/ext/resources/images/editorial/A-New-Big-Images/states/New-York-City.jpg?t=1453402066&width=898"),
                new LocationDbModel("Sofia", 42.4, 23.20,
                        "https://www.godsavethepoints.com/wp-content/uploads/2018/02/sofia-bulgaria.jpeg"),
                new LocationDbModel("Tokyo", 35.652832, 139.839478,
                        "https://www.japan-guide.com/thumb/destination_tokyo.jpg"),
                new LocationDbModel("London", 51.509865, -0.118092,
                        "https://cdn.londonandpartners.com/assets/73295-640x360-london-skyline-ns.jpg"),
                new LocationDbModel("Sydney", -33.838314, 150.999260,
                        "https://s2.thingpic.com/images/AC/ZP5RrCrtTJ83eWDuK9SmUSaf.jpeg"),
                new LocationDbModel("New York", 40.730610, -73.935242,
                        "https://www.housingwire.com/ext/resources/images/editorial/A-New-Big-Images/states/New-York-City.jpg?t=1453402066&width=898"),
                new LocationDbModel("Sofia", 42.4, 23.20,
                        "https://www.godsavethepoints.com/wp-content/uploads/2018/02/sofia-bulgaria.jpeg"),
                new LocationDbModel("Tokyo", 35.652832, 139.839478,
                        "https://www.japan-guide.com/thumb/destination_tokyo.jpg"),
                new LocationDbModel("London", 51.509865, -0.118092,
                        "https://cdn.londonandpartners.com/assets/73295-640x360-london-skyline-ns.jpg"),
                new LocationDbModel("Sydney", -33.838314, 150.999260,
                        "https://s2.thingpic.com/images/AC/ZP5RrCrtTJ83eWDuK9SmUSaf.jpeg"),
                new LocationDbModel("New York", 40.730610, -73.935242,
                        "https://www.housingwire.com/ext/resources/images/editorial/A-New-Big-Images/states/New-York-City.jpg?t=1453402066&width=898"),
                new LocationDbModel("Sofia", 42.4, 23.20,
                        "https://www.godsavethepoints.com/wp-content/uploads/2018/02/sofia-bulgaria.jpeg"),
                new LocationDbModel("Tokyo", 35.652832, 139.839478,
                        "https://www.japan-guide.com/thumb/destination_tokyo.jpg"),
                new LocationDbModel("London", 51.509865, -0.118092,
                        "https://cdn.londonandpartners.com/assets/73295-640x360-london-skyline-ns.jpg")));
    }
}
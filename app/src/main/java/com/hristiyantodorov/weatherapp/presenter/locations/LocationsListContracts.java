package com.hristiyantodorov.weatherapp.presenter.locations;

import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import java.util.List;

public interface LocationsListContracts {

    interface View extends BaseView<Presenter> {

        void showLocations(List<LocationDbModel> locations);

        void getLocationsFromDatabase();

        void setPresenter(Presenter presenter);

        void showLocationWeatherDetails(LocationDbModel selectedLocation);

    }

    interface Presenter extends BasePresenter {

        void loadLocationsFromDatabase();

        void filterLocations(String pattern);

        void selectLocation(LocationDbModel location);

    }

}
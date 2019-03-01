package com.hristiyantodorov.weatherapp.presenter.locations;

import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.temp.BasePresenter;
import com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.temp.BaseView;

import java.util.List;

public interface LocationsListContracts {

    interface View extends BaseView<Presenter> {
//        void showLoader(boolean isShowing);

        void showLocations(List<LocationDbModel> locations);

        void getLocationsFromDatabase();

        void showError(Throwable e);

        void setPresenter(Presenter presenter);

        void showLoading();

        void hideLoading();

        void showLocationWeatherDetails(LocationDbModel selectedLocation);
    }

    interface Presenter extends BasePresenter {

        void loadLocationsFromDatabase();

        void filterLocations(String pattern);

        void selectLocation(LocationDbModel location);
    }

}
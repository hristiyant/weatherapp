package com.hristiyantodorov.weatherapp.presenter.locations;

import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;

import java.util.List;

public interface LocationsListContracts {
    interface View {
        void showLoader(boolean isShowing);

        // TODO: 1/22/2019 Change <String> to the model when ready.
        void showLocations(List<String> locations);

        void showDefaultLocationsList();

        void showError(Throwable e);

        void setPresenter(Presenter presenter);

        void showLocationWeatherDetails();
    }

    interface Presenter {
        void subscribe(View view);

        List<LocationDbModel> loadLocations();

        void filterLocations();

        void selectLocation();
    }
}
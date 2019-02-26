package com.hristiyantodorov.weatherapp.presenter.locations;

import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;

import java.util.List;

public interface LocationsListContracts {
    interface View {
        void showLoader(boolean isShowing);

        // TODO: 1/22/2019 Change <String> to the model when ready.
        void showLocations(List<LocationDbModel> locations);

        void showDefaultLocationsList();

        void showError(Throwable e);

        void setPresenter(Presenter presenter);

        void showLocationWeatherDetails();
    }

    interface Presenter {
        void subscribe(View view);

        void loadLocations();

        void filterLocations(String pattern);

        void selectLocation(LocationDbModel location);
    }
}
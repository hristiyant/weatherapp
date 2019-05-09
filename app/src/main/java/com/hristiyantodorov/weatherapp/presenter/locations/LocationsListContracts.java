package com.hristiyantodorov.weatherapp.presenter.locations;

import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import java.util.List;

public interface LocationsListContracts {

    interface View extends BaseView<Presenter> {

        void showLocations(List<LocationDbModel> locations);

        void setPresenter(Presenter presenter);

        void openWeatherDetailsActivity();
    }

    interface Presenter {

        void loadDbData();

        void fillDbFromApi(List<LocationDbModel> locationDbModels);

        void filterLocations(String pattern);

        void updateLocationDbInfo(LocationDbModel locationDbModel);

        void selectLocation(String lat, String lon);

        void clearDisposables();
    }
}
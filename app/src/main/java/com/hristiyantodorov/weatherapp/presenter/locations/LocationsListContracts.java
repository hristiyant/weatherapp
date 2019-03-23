package com.hristiyantodorov.weatherapp.presenter.locations;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.database.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import java.util.List;

public interface LocationsListContracts {

    interface View extends BaseView<Presenter> {

        void showLocations(List<LocationDbModel> locations);

        void getLocationsFromDatabase();

        void setPresenter(Presenter presenter);

        void getBasicForecastInfo(List<LocationDbModel> locations);

        void updateApiInfo(LocationDbModel location);
    }

    interface Presenter {

        void loadDbData();

        void fillDbFromApi(List<LocationDbModel> locationDbModels);

        void filterLocations(String pattern, Context context);

        void presentLocationsToView(List<LocationDbModel> locations);

        void updateLocationDbInfo(LocationDbModel locationDbModel);
    }
}
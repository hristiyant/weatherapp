package com.hristiyantodorov.weatherapp.presenter.locations;

import android.content.Context;

import com.hristiyantodorov.weatherapp.model.location.LocationDbModel;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

import java.util.List;

public interface LocationsListContracts {

    interface View extends BaseView<Presenter> {

        void showLocations(List<LocationDbModel> locations);

        void getLocationsFromDatabase();

        void setPresenter(Presenter presenter);

        void showEmptyLocationsList();

        void getBasicForecastInfo(List<LocationDbModel> locations);

        void updateApiInfo(LocationDbModel location);
    }

    interface Presenter {

        void filterLocations(String pattern, Context context);

        void presentLocationsToView(List<LocationDbModel> locations);

        void downloadApiDataForDbModels(Context context);

        void getBasicForecastInfo(LocationDbModel location);

        void updateLocationDbInfo(LocationDbModel location);

        void loadDbData(Context context);
    }

}
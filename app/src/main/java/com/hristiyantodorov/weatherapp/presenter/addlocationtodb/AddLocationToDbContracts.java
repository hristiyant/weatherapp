package com.hristiyantodorov.weatherapp.presenter.addlocationtodb;

import com.hristiyantodorov.weatherapp.presenter.BaseView;

public interface AddLocationToDbContracts {

    interface View extends BaseView<Presenter> {

        void setPresenter(AddLocationToDbContracts.Presenter presenter);
    }

    interface Presenter {

        void saveLocationToDb(String name, Double latitude, Double longitude);

        Boolean validateInputString(CharSequence input);

        void clearDisposables();
    }
}

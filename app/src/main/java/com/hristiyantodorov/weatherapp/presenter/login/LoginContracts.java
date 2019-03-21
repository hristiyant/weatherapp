package com.hristiyantodorov.weatherapp.presenter.login;

import com.hristiyantodorov.weatherapp.presenter.BasePresenter;
import com.hristiyantodorov.weatherapp.presenter.BaseView;

public interface LoginContracts {

    interface View extends BaseView<Presenter> {

        void setPresenter(LoginContracts.Presenter presenter);
        // TODO: 1/18/2019 Add showNoUser(), showLoggedUser() (eventually), implement loader

    }

    interface Presenter extends BasePresenter {

        void subscribe(LoginContracts.View view);

        void loginUser(String email);

    }

}
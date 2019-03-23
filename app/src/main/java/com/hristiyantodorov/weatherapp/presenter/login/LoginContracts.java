package com.hristiyantodorov.weatherapp.presenter.login;

import com.hristiyantodorov.weatherapp.presenter.BaseView;

public interface LoginContracts {

    interface View extends BaseView<Presenter> {

        void setPresenter(LoginContracts.Presenter presenter);
    }

    interface Presenter {

        void subscribe(LoginContracts.View view);

        void loginUser(String email);
    }
}
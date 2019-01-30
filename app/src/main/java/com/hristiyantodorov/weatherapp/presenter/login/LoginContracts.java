package com.hristiyantodorov.weatherapp.presenter.login;

public interface LoginContracts {
    interface View {
        void setPresenter(LoginContracts.Presenter presenter);

        void showLoader(boolean isShowing);

        void showError(Throwable e);
        // TODO: 1/18/2019 Add showNoUser(), showLoggedUser() (eventually), implement loader
    }

    interface Presenter {
        void subscribe(LoginContracts.View view);

        void loginUser(String email);
    }
}
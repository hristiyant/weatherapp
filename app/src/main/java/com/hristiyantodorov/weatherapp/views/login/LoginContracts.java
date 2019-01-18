package com.hristiyantodorov.weatherapp.views.login;

public interface LoginContracts {
    interface View {
        void setPresenter(LoginContracts.Presenter presenter);
        void showLoading();
        void hideLoading();
        void showLoader(boolean isShowing);
        void showError(Throwable e);
        // TODO: 1/18/2019 Add showNoUser(), showLoggedUser() (eventually)
    }

    interface Presenter {
        void subscribe(LoginContracts.View view);
        void loginUser(String email);
    }

 }

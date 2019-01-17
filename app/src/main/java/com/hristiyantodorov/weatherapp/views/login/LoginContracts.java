package com.hristiyantodorov.weatherapp.views.login;

public interface LoginContracts {
    interface View {
        void setPresenter(LoginContracts.Presenter presenter);
        void showLoading();
        void hideLoading();
        void showLoader(boolean isShowing)
        void showError(Throwable e);
        //void showNoUser();
        //void showLoggedUswe(User user);
    }

    interface Presenter {
        void subscribe(LoginContracts.View view);
        void loginUser(String email);
    }

 }

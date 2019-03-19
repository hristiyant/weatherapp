package com.hristiyantodorov.weatherapp.presenter;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoader(boolean isVisible);

    void showError(Exception e);

}

package com.hristiyantodorov.weatherapp.presenter;

public interface BaseView<T> {
    void setPresenter(T presenter);
    //void showToast(String text);
    void showLoading();
    void hideLoading();
}

package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.temp;

public interface BaseView<T> {
    void setPresenter(T presenter);
    //void showToast(String text);
    void showLoading();
    void hideLoading();
}

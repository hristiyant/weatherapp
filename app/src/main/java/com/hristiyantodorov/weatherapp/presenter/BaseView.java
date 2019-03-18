package com.hristiyantodorov.weatherapp.presenter;

public interface BaseView<T> {
    void setPresenter(T presenter);
    //void showToast(String text);
    void showLoader(boolean isShowing);

    void showError(Throwable e);
}

package com.hristiyantodorov.weatherapp.presenter;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoader(boolean isShowing);

    void showEmptyScreen(boolean isShowing);

    void showError(Throwable e);
}

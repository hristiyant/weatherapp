package com.hristiyantodorov.weatherapp.ui.fragment.weatherdetails.temp;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showLoader(boolean isVisible);

    void showError(Throwable e);

}

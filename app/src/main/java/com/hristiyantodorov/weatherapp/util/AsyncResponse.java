package com.hristiyantodorov.weatherapp.util;

public interface AsyncResponse<T> {
    void onSuccess(T output);

    void onFailure(Exception e);
}

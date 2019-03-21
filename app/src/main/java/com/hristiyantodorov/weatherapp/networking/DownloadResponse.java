package com.hristiyantodorov.weatherapp.networking;

public interface DownloadResponse<T> {

    void onSuccess(T object);

    void onFailure(Exception e);

}

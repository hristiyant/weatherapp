package com.hristiyantodorov.weatherapp.service;

import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.retrofit.APIClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Call;

@Singleton
public class ForecastApiService {

    public APIClient apiClient;

    @Inject
    public ForecastApiService(APIClient apiClient) {
        this.apiClient = apiClient;
    }

    public Call<ForecastFullResponse> getFullForecastData(String latitude, String longitude) {
        return apiClient.getWeatherApi().getFullForecastData(latitude, longitude);
    }

    public Single<ForecastFullResponse> getForecastCurrently(String latitude, String longitude, String language) {
        return apiClient.getWeatherApi().getForecastCurrently(latitude, longitude, language);
    }

    public Single<ForecastFullResponse> getForecastFullResponse(String latitude, String longitude, String language) {
        return apiClient.getWeatherApi().getForecastFullResponse(latitude, longitude, language);
    }

    public Single<ForecastFullResponse> getForecastHourly(String latitude, String longitude) {
        return apiClient.getWeatherApi().getForecastHourly(latitude, longitude);
    }

    public Single<ForecastFullResponse> getForecastDaily(String latitude, String longitude) {
        return apiClient.getWeatherApi().getForecastDaily(latitude, longitude);
    }
}

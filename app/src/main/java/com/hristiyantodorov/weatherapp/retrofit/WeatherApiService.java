package com.hristiyantodorov.weatherapp.retrofit;

import com.hristiyantodorov.weatherapp.model.response.ForecastFullResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApiService {

    /**
     * Downloads the full forecast data (currently, hourly and daily) from the API.
     *
     * @param latitude
     * @param longitude
     * @return
     */
    @GET("{latitude},{longitude}?exclude=minutely,alerts,flags")
    Call<ForecastFullResponse> getFullForecastData(@Path("latitude") String latitude,
                                                   @Path("longitude") String longitude);

    /**
     * Downloads forecast data (currently ONLY) from the API.
     *
     * @param latitude
     * @param longitude
     * @return
     */
    @GET("{latitude},{longitude}?exclude=minutely,hourly,daily,alerts,flags")
    Single<ForecastFullResponse> getForecastCurrently(@Path("latitude") String latitude,
                                                      @Path("longitude") String longitude,
                                                      @Query("lang") String language);

    @GET("{latitude},{longitude}?exclude=minutely,alerts,flags")
    Single<ForecastFullResponse> getForecastFullResponse(@Path("latitude") String latitude,
                                                         @Path("longitude") String longitude,
                                                         @Query("lang") String language);

    /**
     * Downloads hourly forecast data (currently AND hourly) from the API.
     *
     * @param latitude
     * @param longitude
     * @return
     */
    @GET("{latitude},{longitude}?exclude=minutely,daily,alerts,flags")
    Single<ForecastFullResponse> getForecastHourly(@Path("latitude") String latitude,
                                                   @Path("longitude") String longitude);

    /**
     * Downloads daily forecast data (currently AND daily) from the API.
     *
     * @param latitude
     * @param longitude
     * @return
     */
    @GET("{latitude},{longitude}?exclude=minutely,hourly,alerts,flags")
    Single<ForecastFullResponse> getForecastDaily(@Path("latitude") String latitude,
                                                  @Path("longitude") String longitude);
}
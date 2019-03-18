package com.hristiyantodorov.weatherapp.util.retrofit;

import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

    //TODO: ADD "daily,hourly" to excluded fields. !!!!!!!!!!!!!!
    @GET("{latitude},{longitude}?exclude=minutely,hourly,daily,alerts,flags")
    Single<ForecastFullResponse> getForecastCurrently(@Path("latitude") String latitude,
                                                               @Path("longitude") String longitude);

    //TODO: ADD "daily,hourly" to excluded fields. !!!!!!!!!!!!!!
    @GET("{latitude},{longitude}?exclude=minutely,alerts,flags")
    Observable<ForecastFullResponse> getForecastFullResponse(@Path("latitude") String latitude,
                                                             @Path("longitude") String longitude);


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
package com.hristiyantodorov.weatherapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class APIClient {

    private WeatherApi weatherApi;
    private static final int REQUEST_TIMEOUT = 60;
    private static final Object LOCK = new Object();

    @Inject
    public APIClient() {
    }

    private Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    // TODO: 28.3.2019 provideCache

    private OkHttpClient buildOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new ChuckInterceptor(App.getInstance().getApplicationContext()));
        // TODO: 28.3.2019 add Cache
        return httpClient.build();
    }

    private Retrofit buildRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BaseUrl + BuildConfig.ApiKey + "/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient);

        return retrofit.build();
    }

    public WeatherApi getWeatherApi() {
        if (weatherApi == null) {
            synchronized (LOCK) {
                Retrofit retrofit = buildRetrofit(createGson(), buildOkHttpClient());
                weatherApi = retrofit.create(WeatherApi.class);
            }
        }
        return weatherApi;
    }
}
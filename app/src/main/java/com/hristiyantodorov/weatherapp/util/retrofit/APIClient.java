package com.hristiyantodorov.weatherapp.util.retrofit;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.BuildConfig;
import com.readystatesoftware.chuck.ChuckInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String API_KEY = BuildConfig.ApiKey;
    private static final String BASE_URL = BuildConfig.ApiBaseUrl;

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new ChuckInterceptor(App.getInstance().getApplicationContext()))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + API_KEY + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
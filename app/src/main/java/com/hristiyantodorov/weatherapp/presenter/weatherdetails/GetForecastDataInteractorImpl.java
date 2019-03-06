package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import com.hristiyantodorov.weatherapp.util.Constants;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.util.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.util.retrofit.APIInterface;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetForecastDataInteractorImpl implements WeatherDetailsContracts.GetForecastDataInteractor {
    @Override
    public void getForecastCurrentlyResponse(final OnFinishedListener onFinishedListener) {
        APIInterface service = APIClient.getClient().create(APIInterface.class);

        Call<ForecastFullResponse> call = service.getFullForecastData(
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LAT,null),
                SharedPrefUtil.read(Constants.SHARED_PREF_LOCATION_LON,null)
        );

        call.enqueue(new Callback<ForecastFullResponse>() {
            @Override
            public void onResponse(Call<ForecastFullResponse> call, Response<ForecastFullResponse> response) {
                onFinishedListener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<ForecastFullResponse> call, Throwable t) {
                onFinishedListener.onFailed(t);
            }
        });
    }
}

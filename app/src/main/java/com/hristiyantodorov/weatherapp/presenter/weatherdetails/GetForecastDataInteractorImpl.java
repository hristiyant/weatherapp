package com.hristiyantodorov.weatherapp.presenter.weatherdetails;

import com.hristiyantodorov.weatherapp.util.retrofit.APIClient;
import com.hristiyantodorov.weatherapp.util.retrofit.WeatherApiService;

public class GetForecastDataInteractorImpl implements WeatherDetailsContracts.GetForecastDataInteractor {
    @Override
    public void getForecastCurrentlyResponse(final OnFinishedListener onFinishedListener) {
        WeatherApiService service = APIClient.getClient().create(WeatherApiService.class);

        /*Call<ForecastFullResponse> call = service.getForecastCurrently(
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
        });*/
    }
}

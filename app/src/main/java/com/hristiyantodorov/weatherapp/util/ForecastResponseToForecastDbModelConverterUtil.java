package com.hristiyantodorov.weatherapp.util;

import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastCurrentlyResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;

public class ForecastResponseToForecastDbModelConverterUtil {

    public static ForecastFullDbModel convertResponseToDbModel(ForecastFullResponse forecastFullResponse) {
        ForecastFullDbModel fullDbModel = new ForecastFullDbModel();
        fullDbModel.setLatitude(forecastFullResponse.getLatitude());
        fullDbModel.setLongitude(forecastFullResponse.getLongitude());
        fullDbModel.setTimezone(forecastFullResponse.getTimezone());

        ForecastCurrentlyResponse currentlyResponse = forecastFullResponse.getCurrently();
        ForecastCurrentlyDbModel currentlyDbModel = new ForecastCurrentlyDbModel();
        currentlyDbModel.setApparentTemperature(currentlyResponse.getApparentTemperature());
        currentlyDbModel.setHumidity(currentlyResponse.getHumidity());
        return fullDbModel;
    }

    public static ForecastCurrentlyDbModel convertCurrentelyResponseToDbModel(ForecastCurrentlyResponse forecastCurrentlyResponse) {
        ForecastCurrentlyDbModel currentlyDbModel = new ForecastCurrentlyDbModel();
        currentlyDbModel.setApparentTemperature(forecastCurrentlyResponse.getApparentTemperature());
        currentlyDbModel.setHumidity(forecastCurrentlyResponse.getHumidity());
        return currentlyDbModel;
    }
}

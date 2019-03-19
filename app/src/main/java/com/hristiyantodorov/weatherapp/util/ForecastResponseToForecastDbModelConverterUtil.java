package com.hristiyantodorov.weatherapp.util;

import com.hristiyantodorov.weatherapp.model.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastDailyDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.model.forecast.ForecastHourlyDbModel;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastCurrentlyResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastDailyDataResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastDailyResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastHourlyResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ForecastResponseToForecastDbModelConverterUtil {

    public static ForecastFullDbModel convertResponseToDbModel(ForecastFullResponse fullResponse) {
        ForecastFullDbModel fullDbModel = new ForecastFullDbModel();
        fullDbModel.setLatitude(fullResponse.getLatitude());
        fullDbModel.setLongitude(fullResponse.getLongitude());
        fullDbModel.setTimezone(fullResponse.getTimezone());
        fullDbModel.setLastUpdatedTimestamp(getCurrentTimestamp());
        fullDbModel.setCurrentlyDbModel(convertCurrentlyResponseToDbModel(fullResponse.getCurrently()));
        fullDbModel.setHourlyDbModel(convertHourlyResponseToDbModel(fullResponse.getHourly()));
        fullDbModel.setHourlyDataDbModels(convertHourlyDataResponseListToDbModelList(fullResponse.getHourly().getData()));
        fullDbModel.setDailyDbModel(convertDailyResponseToDbModel(fullResponse.getDaily()));
        fullDbModel.setDailyDataDbModels(convertDailyDataResponseListToDbModelList(fullResponse.getDaily().getData()));


        return fullDbModel;
    }

    public static ForecastCurrentlyDbModel convertCurrentlyResponseToDbModel(ForecastCurrentlyResponse currentlyResponse) {
        ForecastCurrentlyDbModel currentlyDbModel = new ForecastCurrentlyDbModel();

        currentlyDbModel.setTime(new SimpleDateFormat("HH:mm")
                .format(new java.util.Date(currentlyResponse.getTime() * 1000)));
        currentlyDbModel.setSummary(currentlyResponse.getSummary());
        currentlyDbModel.setIcon(currentlyResponse.getIcon());
        currentlyDbModel.setTemperature(currentlyResponse.getTemperature());
        currentlyDbModel.setApparentTemperature(currentlyResponse.getApparentTemperature());
        currentlyDbModel.setHumidity(currentlyResponse.getHumidity());
        currentlyDbModel.setPressure(currentlyResponse.getPressure());
        currentlyDbModel.setWindSpeed(currentlyResponse.getWindSpeed());

        return currentlyDbModel;
    }

    public static ForecastHourlyDbModel convertHourlyResponseToDbModel(ForecastHourlyResponse HourlyResponse) {
        ForecastHourlyDbModel hourlyDbModel = new ForecastHourlyDbModel();

        hourlyDbModel.setIcon(HourlyResponse.getIcon());
        hourlyDbModel.setSummary(HourlyResponse.getSummary());

        return hourlyDbModel;
    }

    public static List<ForecastCurrentlyDbModel> convertHourlyDataResponseListToDbModelList(List<ForecastCurrentlyResponse> hourlyDataResponse) {
        List<ForecastCurrentlyDbModel> hourlyDataDbModels = new ArrayList<>();

        for (ForecastCurrentlyResponse hourlyResponse : hourlyDataResponse) {
            hourlyDataDbModels.add(convertCurrentlyResponseToDbModel(hourlyResponse));
        }

        return hourlyDataDbModels;
    }

    public static ForecastDailyDbModel convertDailyResponseToDbModel(ForecastDailyResponse dailyResponse) {
        ForecastDailyDbModel dailyDbModel = new ForecastDailyDbModel();

        dailyDbModel.setIcon(dailyResponse.getIcon());
        dailyDbModel.setSummary(dailyResponse.getSummary());

        return dailyDbModel;
    }

    public static List<ForecastDailyDataDbModel> convertDailyDataResponseListToDbModelList(List<ForecastDailyDataResponse> dailyDataResponse) {
        List<ForecastDailyDataDbModel> dailyDataDbModels = new ArrayList<>();

        for (ForecastDailyDataResponse dailyResponse : dailyDataResponse) {
            dailyDataDbModels.add(convertDailyDataResponseToDbModel(dailyResponse));
        }

        return dailyDataDbModels;
    }

    public static ForecastDailyDataDbModel convertDailyDataResponseToDbModel(ForecastDailyDataResponse dailyDataResponse) {
        ForecastDailyDataDbModel dailyDataDbModel = new ForecastDailyDataDbModel();

        dailyDataDbModel.setHumidity(dailyDataResponse.getHumidity());
        dailyDataDbModel.setIcon(dailyDataResponse.getIcon());
        dailyDataDbModel.setPressure(dailyDataResponse.getPressure());
        dailyDataDbModel.setPressure(dailyDataResponse.getPressure());
        dailyDataDbModel.setSummary(dailyDataResponse.getSummary());
        dailyDataDbModel.setSunriseTime(new SimpleDateFormat("HH:mm")
                .format(new java.util.Date(dailyDataResponse.getSunriseTime() * 1000)));
        dailyDataDbModel.setSunsetTime(new SimpleDateFormat("HH:mm")
                .format(new java.util.Date(dailyDataResponse.getSunsetTime() * 1000)));
        dailyDataDbModel.setTemperatureMax(dailyDataResponse.getTemperatureMax());
        dailyDataDbModel.setTemperatureMaxTime(new SimpleDateFormat("HH:mm")
                .format(new java.util.Date(dailyDataResponse.getTemperatureMaxTime() * 1000)));
        dailyDataDbModel.setTemperatureMin(dailyDataResponse.getTemperatureMin());
        dailyDataDbModel.setTemperatureMinTime(new SimpleDateFormat("HH:mm")
                .format(new java.util.Date(dailyDataResponse.getTemperatureMinTime() * 1000)));
        dailyDataDbModel.setTime(new SimpleDateFormat("EEEE")
                .format(new java.util.Date(dailyDataResponse.getTime() * 1000)));
        dailyDataDbModel.setWindSpeed(dailyDataResponse.getWindSpeed());

        return dailyDataDbModel;
    }

    private static String getCurrentTimestamp() {
        return DateFormat
                .getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault())
                .format(new java.util.Date());
    }

}
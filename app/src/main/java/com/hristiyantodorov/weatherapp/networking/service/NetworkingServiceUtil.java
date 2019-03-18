package com.hristiyantodorov.weatherapp.networking.service;

import android.os.AsyncTask;
import android.util.JsonReader;

import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastCurrentlyResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastDailyDataResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastDailyResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastFullResponse;
import com.hristiyantodorov.weatherapp.util.retrofit.model.ForecastHourlyResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NetworkingServiceUtil {

    private static final String URL_PREFIX = "https://api.darksky.net/forecast/09ab310ab4796f158888f52a6b5fa900/";
    private static final String URL_SUFFIX_CURRENTLY = "?exclude=minutely,hourly,daily,alerts,flags";
    private static final String URL_SUFFIX_HOURLY = "?exclude=currently,minutely,daily,alerts,flags";
    private static final String URL_SUFFIX_DAILY = "?exclude=currently,minutely,hourly,alerts,flags";

    private String latitude = SharedPrefUtil.read("MY_LATITUDE", null);
    private String longitude = SharedPrefUtil.read("MY_LONGITUDE", null);

    public void getWeatherDataCurrently(DownloadResponse callback, String latitude, String longitude) {
        String url = URL_PREFIX + latitude + "," + longitude + URL_SUFFIX_CURRENTLY;
        new DownloadTaskCurrently(callback).execute(url);
    }

    public void getWeatherDataHourly(DownloadResponse callback, String latitude, String longitude) {
        String url = URL_PREFIX + latitude + "," + longitude + URL_SUFFIX_HOURLY;
        new DownloadTaskCurrently(callback).execute(url);
    }

    public void getWeatherDataDaily(DownloadResponse callback, String latitude, String longitude) {
        String url = URL_PREFIX + latitude + "," + longitude + URL_SUFFIX_DAILY;
        new DownloadTaskCurrently(callback).execute(url);
    }

    public static class DownloadTaskCurrently extends AsyncTask<String, Integer, ForecastFullResponse> {

        private DownloadResponse callback;
        private Exception exception;

        DownloadTaskCurrently(DownloadResponse callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ForecastFullResponse doInBackground(String... urls) {
            ForecastFullResponse finalResult = null;
            if (!isCancelled()) {
                URL url = null;
                try {
                    url = new URL(urls[0]);
                    finalResult = downloadUrl(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return finalResult;

            }
            return null;
        }

        @Override
        protected void onPostExecute(ForecastFullResponse result) {
            if (callback != null) {
                if (exception == null) {
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(exception);
                }
            }
        }
    }

    private static ForecastFullResponse downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpURLConnection connection = null;
        ForecastFullResponse result = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            // publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);
            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Http error code: " + responseCode);
            }
            stream = connection.getInputStream();
            // publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0);
            if (stream != null) {
                result = readJsonStream(new InputStreamReader(stream, "UTF-8"));
                //publishProgress(DownloadCallback.Progress.PROCESS_INPUT_STREAM_SUCCESS, 0);
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    //Converts the contents of an InputStream to the desired data model class
    private static ForecastFullResponse readJsonStream(InputStreamReader inputStreamReader) throws IOException {
        JsonReader jsonReader = new JsonReader(inputStreamReader);
        return readFullData(jsonReader);
    }

    private static ForecastFullResponse readFullData(JsonReader reader) throws IOException {

        ForecastFullResponse fullResponse = new ForecastFullResponse();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "latitude":
                    fullResponse.setLatitude(reader.nextDouble());
                    break;
                case "longitude":
                    fullResponse.setLongitude(reader.nextDouble());
                    break;
                case "timezone":
                    fullResponse.setTimezone(reader.nextString());
                    break;
                case "currently":
                    fullResponse.setCurrently(readItemHourly(reader));
                    break;
                case "hourly":
                    fullResponse.setHourly(readForecastHourly(reader));
                    break;
                case "daily":
                    fullResponse.setDaily(readForecastDaily(reader));
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return fullResponse;
    }

    private static ForecastHourlyResponse readForecastHourly(JsonReader reader) throws IOException {

        ForecastHourlyResponse result = new ForecastHourlyResponse();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "summary":
                    result.setSummary(reader.nextString());
                    break;
                case "icon":
                    result.setIcon(reader.nextString());
                    break;
                case "data":
                    result.setData(readHourly(reader));
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return result;
    }

    private static List<ForecastCurrentlyResponse> readHourly(JsonReader reader) throws IOException {

        List<ForecastCurrentlyResponse> result = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            result.add(readItemHourly(reader));
        }
        reader.endArray();
        return result;
    }

    private static ForecastCurrentlyResponse readItemHourly(JsonReader reader) throws IOException {

        ForecastCurrentlyResponse currentlyResponse = new ForecastCurrentlyResponse();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "time":
                    currentlyResponse.setTime(reader.nextLong());
                    break;
                case "summary":
                    currentlyResponse.setSummary(reader.nextString());
                    break;
                case "icon":
                    currentlyResponse.setIcon(reader.nextString());
                    break;
                case "temperature":
                    currentlyResponse.setTemperature(reader.nextDouble());
                    break;
                case "apparentTemperature":
                    currentlyResponse.setApparentTemperature(reader.nextDouble());
                    break;
                case "humidity":
                    currentlyResponse.setHumidity(reader.nextDouble());
                    break;
                case "pressure":
                    currentlyResponse.setPressure(reader.nextDouble());
                    break;
                case "windSpeed":
                    currentlyResponse.setWindSpeed(reader.nextDouble());
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return currentlyResponse;
    }

    private static ForecastDailyResponse readForecastDaily(JsonReader reader) throws IOException {

        ForecastDailyResponse result = new ForecastDailyResponse();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "summary":
                    result.setSummary(reader.nextString());
                    break;
                case "icon":
                    result.setIcon(reader.nextString());
                    break;
                case "data":
                    result.setData(readDaily(reader));
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return result;
    }

    private static List<ForecastDailyDataResponse> readDaily(JsonReader reader) throws IOException {

        List<ForecastDailyDataResponse> result = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            result.add(readItemDaily(reader));
        }
        reader.endArray();
        return result;
    }

    private static ForecastDailyDataResponse readItemDaily(JsonReader reader) throws IOException {

        ForecastDailyDataResponse dailyDataResponse = new ForecastDailyDataResponse();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "time":
                    dailyDataResponse.setTime(reader.nextLong());
                    break;
                case "summary":
                    dailyDataResponse.setSummary(reader.nextString());
                    break;
                case "icon":
                    dailyDataResponse.setIcon(reader.nextString());
                    break;
                case "sunriseTime":
                    dailyDataResponse.setSunriseTime(reader.nextLong());
                    break;
                case "sunsetTime":
                    dailyDataResponse.setSunsetTime(reader.nextLong());
                    break;
                case "humidity":
                    dailyDataResponse.setHumidity(reader.nextDouble());
                    break;
                case "windSpeed":
                    dailyDataResponse.setWindSpeed(reader.nextDouble());
                    break;
                case "temperatureMin":
                    dailyDataResponse.setTemperatureMin(reader.nextDouble());
                    break;
                case "temperatureMax":
                    dailyDataResponse.setTemperatureMax(reader.nextDouble());
                    break;
                case "temperatureMinTime":
                    dailyDataResponse.setTemperatureMinTime(reader.nextLong());
                    break;
                case "temperatureMaxTime":
                    dailyDataResponse.setTemperatureMaxTime(reader.nextLong());
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return dailyDataResponse;
    }
/*
    public Single<ForecastFullResponse> getForecastFullResponse(String latitude, String longitude){
        return null;
    }*/
}
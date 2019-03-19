package com.hristiyantodorov.weatherapp.networking.service;

import android.os.AsyncTask;
import android.util.JsonReader;

import com.hristiyantodorov.weatherapp.model.weather.ForecastDataDaily;
import com.hristiyantodorov.weatherapp.model.weather.ForecastDataHourly;
import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.model.weather.WeatherDataCurrently;
import com.hristiyantodorov.weatherapp.model.weather.WeatherDataDaily;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NetworkingService {

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

    public static class DownloadTaskCurrently extends AsyncTask<String, Integer, WeatherData> {

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
        protected WeatherData doInBackground(String... urls) {
            WeatherData finalResult = null;
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
        protected void onPostExecute(WeatherData result) {
            if (callback != null) {
                if (exception == null) {
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(exception);
                }
            }
        }
    }

    private static WeatherData downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpURLConnection connection = null;
        WeatherData result = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Http error code: " + responseCode);
            }
            stream = connection.getInputStream();
            if (stream != null) {
                result = readJsonStream(new InputStreamReader(stream, "UTF-8"));
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

    /**Converts the contents of an InputStream to the desired data model class
     *
     * @param inputStreamReader
     * @return
     * @throws IOException
     */
    private static WeatherData readJsonStream(InputStreamReader inputStreamReader) throws IOException {
        JsonReader jsonReader = new JsonReader(inputStreamReader);
        return readFullData(jsonReader);
    }

    private static WeatherData readFullData(JsonReader reader) throws IOException {

        WeatherData weatherData = new WeatherData();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "latitude":
                    weatherData.setLatitude(reader.nextDouble());
                    break;
                case "longitude":
                    weatherData.setLongitude(reader.nextDouble());
                    break;
                case "timezone":
                    weatherData.setTimezone(reader.nextString());
                    break;
                case "currently":
                    weatherData.setCurrently(readItemHourly(reader));
                    break;
                case "hourly":
                    weatherData.setHourly(readForecastHourly(reader));
                    break;
                case "daily":
                    weatherData.setDaily(readForecastDaily(reader));
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return weatherData;
    }

    private static ForecastDataHourly readForecastHourly(JsonReader reader) throws IOException {

        ForecastDataHourly result = new ForecastDataHourly();

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

    private static List<WeatherDataCurrently> readHourly(JsonReader reader) throws IOException {

        List<WeatherDataCurrently> result = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            result.add(readItemHourly(reader));
        }
        reader.endArray();
        return result;
    }

    private static WeatherDataCurrently readItemHourly(JsonReader reader) throws IOException {

        WeatherDataCurrently forecastDataCurrently = new WeatherDataCurrently();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "time":
                    forecastDataCurrently.setTime(reader.nextInt());
                    break;
                case "summary":
                    forecastDataCurrently.setSummary(reader.nextString());
                    break;
                case "icon":
                    forecastDataCurrently.setIcon(reader.nextString());
                    break;
                case "temperature":
                    forecastDataCurrently.setTemperature(reader.nextDouble());
                    break;
                case "apparentTemperature":
                    forecastDataCurrently.setApparentTemperature(reader.nextDouble());
                    break;
                case "humidity":
                    forecastDataCurrently.setHumidity(reader.nextDouble());
                    break;
                case "pressure":
                    forecastDataCurrently.setPressure(reader.nextDouble());
                    break;
                case "windSpeed":
                    forecastDataCurrently.setWindSpeed(reader.nextDouble());
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return forecastDataCurrently;
    }

    private static ForecastDataDaily readForecastDaily(JsonReader reader) throws IOException {

        ForecastDataDaily result = new ForecastDataDaily();

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

    private static List<WeatherDataDaily> readDaily(JsonReader reader) throws IOException {

        List<WeatherDataDaily> result = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            result.add(readItemDaily(reader));
        }
        reader.endArray();
        return result;
    }

    private static WeatherDataDaily readItemDaily(JsonReader reader) throws IOException {

        WeatherDataDaily weatherDataCurrently = new WeatherDataDaily();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "time":
                    weatherDataCurrently.setTime(reader.nextInt());
                    break;
                case "summary":
                    weatherDataCurrently.setSummary(reader.nextString());
                    break;
                case "icon":
                    weatherDataCurrently.setIcon(reader.nextString());
                    break;
                case "sunriseTime":
                    weatherDataCurrently.setSunriseTime(reader.nextLong());
                    break;
                case "sunsetTime":
                    weatherDataCurrently.setSunsetTime(reader.nextLong());
                    break;
                case "humidity":
                    weatherDataCurrently.setHumidity(reader.nextDouble());
                    break;
                case "windSpeed":
                    weatherDataCurrently.setWindSpeed(reader.nextDouble());
                    break;
                case "temperatureMin":
                    weatherDataCurrently.setTemperatureMin(reader.nextDouble());
                    break;
                case "temperatureMax":
                    weatherDataCurrently.setTemperatureMax(reader.nextDouble());
                    break;
                case "temperatureMinTime":
                    weatherDataCurrently.setTemperatureMinTime(reader.nextLong());
                    break;
                case "temperatureMaxTime":
                    weatherDataCurrently.setTemperatureMaxTime(reader.nextLong());
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return weatherDataCurrently;
    }
}
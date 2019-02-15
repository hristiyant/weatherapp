package com.hristiyantodorov.weatherapp.networking.service;

import android.os.AsyncTask;
import android.util.JsonReader;

import com.hristiyantodorov.weatherapp.model.weather.WeatherData;
import com.hristiyantodorov.weatherapp.model.weather.WeatherDataCurrently;
import com.hristiyantodorov.weatherapp.networking.DownloadResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkingServiceUtil {

    public void getWeatherData(DownloadResponse callback) {
        new DownloadTask(callback).execute();
    }

    public static class DownloadTask extends AsyncTask<Void, Integer, WeatherData> {
        private DownloadResponse callback;
        private Exception exception;

        DownloadTask(DownloadResponse callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected WeatherData doInBackground(Void... voids) {
            WeatherData finalResult = null;
            if (!isCancelled()) {

                URL url = null;
                try {
                    url = new URL("https://api.darksky.net/forecast/09ab310ab4796f158888f52a6b5fa900/42.69751,23.32415?exclude=minutely,hourly,daily,alerts,flags");
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
    private static WeatherData readJsonStream(InputStreamReader inputStreamReader) throws IOException {
        JsonReader jsonReader = new JsonReader(inputStreamReader);
        return readData(jsonReader);
    }

    private static WeatherData readData(JsonReader reader) throws IOException {

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
                    weatherData.setCurrently(readCurrently(reader));
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return weatherData;
    }

    private static WeatherDataCurrently readCurrently(JsonReader reader) throws IOException {

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
}
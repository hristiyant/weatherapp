package com.hristiyantodorov.weatherapp.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkClass {

    public static final String TAG = "NetworkClass";
    private static final String URL_KEY = "UrlKey";

    private DownloadCallback callback;
    private DownloadTask downloadTask;
    private String urlString;

    public NetworkClass(String url) {
        this.urlString = url;
    }

    private class DownloadTask extends AsyncTask<String, Integer, DownloadTask.Result> {

        class Result {
            public String resultValue;
            public Exception exception;

            public Result(String resultValue) {
                this.resultValue = resultValue;
            }

            public Result(Exception exception) {
                this.exception = exception;
            }
        }

        @Override
        protected void onPreExecute() {
            if (callback != null) {
                NetworkInfo networkInfo = callback.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() ||
                        (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                    // If no connectivity, cancel task and update Callback with null data.
                    callback.updateFromDownload(null);
                    cancel(true);
                }
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            if (result != null && callback != null) {
                if (result.exception != null) {
                    callback.updateFromDownload(result.exception.getMessage());
                } else if (result.resultValue != null) {
                    callback.updateFromDownload(result.resultValue);
                }
                callback.finishDownloading();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values.length >= 2) {
                callback.onProgressUpdate(values[0], values[1]);
            }
        }

        @Override
        protected void onCancelled(Result result) {
            super.onCancelled(result);
        }

        //Defines work to perform on the background thread.
        @Override
        protected Result doInBackground(String... urls) {
            Result result = null;
            if (!isCancelled() && urls != null && urls.length > 0) {
                String urlString = urls[0];
                try {
                    URL url = new URL(urlString);
                    String resultString = downloadUrl(url);
                    if (resultString != null) {
                        result = new Result(resultString);
                    } else {
                        throw new IOException("No response received.");
                    }
                } catch (Exception e) {
                    result = new Result(e);
                }
            }
            return result;
        }

        private String downloadUrl(URL url) throws IOException {
            InputStream stream = null;
            HttpURLConnection connection = null;
            String result = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                // For this use case, setting HTTP method to GET.
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);
                int responseCode = connection.getResponseCode();

                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Http error code: " + responseCode);
                }

                // Retrieve the response body as an InputStream.
                stream = connection.getInputStream();
                publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0);
                if (stream != null) {
                    // Converts Stream to String with max length of 500.
                    result = readStream(stream, 500);
                    publishProgress(DownloadCallback.Progress.PROCESS_INPUT_STREAM_SUCESS, 0);
                }
            } finally {
                // Close Stream and disconnect HTTPS connection.
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        //Converts the contents of an InputStream to a String
        private String readStream(InputStream stream, int maxLength) throws IOException {
            String result = null;
            //Read InputStream using UTF-8 charset
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            //Create temporary buffer to hold Stream data with specified max length
            char[] buffer = new char[maxLength];
            //Populate temporary buffer with Stream data
            int numChars = 0;
            int readSize = 0;
            while (numChars < maxLength && readSize != -1) {
                numChars += readSize;
                int pct = (100 * numChars) / maxLength;
                publishProgress(DownloadCallback.Progress.PROCESS_INPUT_STREAM_IN_PROGRESS, pct);
                readSize = reader.read(buffer, numChars, buffer.length - numChars);
            }
            if (numChars != -1) {
                /*The stream was not empty.
                  Create String that is actual length of response body if actual length was less
                  than max length.*/
                numChars = Math.min(numChars, maxLength);
                result = new String(buffer, 0, numChars);
            }
            return result;
        }
    }
}

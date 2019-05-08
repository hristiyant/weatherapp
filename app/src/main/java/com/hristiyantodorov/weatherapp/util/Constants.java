package com.hristiyantodorov.weatherapp.util;

public class Constants {
    public static final String KEY_PERSISTENCE_FORECAST_OBJECT = "KEY_PERSISTENCE_FORECAST_OBJECT";

    public static final String EXTRA_KEY_LATITUDE = "EXTRA_LATITUDE";
    public static final String EXTRA_KEY_LONGITUDE = "EXTRA_LONGITUDE";

    public static final int TIMESTAMP_MILLIS_MULTIPLIER = 1000;
    public static final int DEBOUNCE_DELAY_MILLIS = 300;

    public static final String SHARED_PREF_LOCATION_LAT = "SHARED_PREF_LOCATION_LAT";
    public static final String SHARED_PREF_LOCATION_LON = "SHARED_PREF_LOCATION_LON";
    public static final String FEEDBACK_KEY = "feedback";
    public static final String LANGUAGE_KEY = "shared_pref_api_content_lang_key";

    //CurrentLocationPickerUtil
    public static final int MIN_TIME = 1000;
    public static final int MIN_DISTANCE = 0;

    //Weather Icons
    public static final String ICON_CLEAR_DAY = "clear-day";
    public static final String ICON_CLEAR_NIGHT = "clear-night";
    public static final String ICON_RAIN = "rain";
    public static final String ICON_SNOW = "snow";
    public static final String ICON_SLEET = "sleet";
    public static final String ICON_WIND = "wind";
    public static final String ICON_FOG = "fog";
    public static final String ICON_CLOUDY = "cloudy";
    public static final String ICON_PARTLY_CLOUDY_DAY = "partly-cloudy-day";
    public static final String ICON_PARTLY_CLOUDY_NIGHT = "partly-cloudy-night";

    //Json Response field names
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String TIMEZONE = "timezone";
    public static final String CURRENTLY = "currently";
    public static final String HOURLY = "hourly";
    public static final String DAILY = "daily";
    public static final String SUMMARY = "summary";
    public static final String ICON = "icon";
    public static final String DATA = "data";
    public static final String TIME = "time";
    public static final String TEMPERATURE = "temperature";
    public static final String APP_TEMPERATURE = "apparentTemperature";
    public static final String HUMIDITY = "humidity";
    public static final String PRESSURE = "pressure";
    public static final String WIND_SPEED = "windSpeed";
    public static final String SUNRISE_TIME = "sunriseTime";
    public static final String SUNSET_TIME = "sunsetTime";
    public static final String TEMP_MIN = "temperatureMin";
    public static final String TEMP_MAX = "temperatureMax";
    public static final String TEMP_MIN_TIME = "temperatureMinTime";
    public static final String TEMP_MAX_TIME = "temperatureMaxTime";
    public static final String REGEX_LOCATION_NAME = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
    public static final String REGEX_LOCATION_COORDINATES = "[0-9]{1,13}(\\.[0-9]*)?";
}
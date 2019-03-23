package com.hristiyantodorov.weatherapp.util;

import android.support.annotation.DrawableRes;

import com.hristiyantodorov.weatherapp.R;

public class WeatherIconPickerUtil {

    /**
     * Picks which weather image to be displayed based on the icon string received from the API.
     *
     * @param icon
     * @return
     */
    @DrawableRes
    public static int pickWeatherIcon(String icon) {
        switch (icon) {
            case "clear-day":
                return R.drawable.ic_clear_day;
            case "clear-night":
                return R.drawable.ic_clear_night;
            case "rain":
                return R.drawable.ic_rain;
            case "snow":
                return R.drawable.ic_snow;
            case "sleet":
                return R.drawable.ic_sleet;
            case "wind":
                return R.drawable.ic_wind;
            case "fog":
                return R.drawable.ic_fog;
            case "cloudy":
                return R.drawable.ic_cloudy;
            case "partly-cloudy-day":
                return R.drawable.ic_partly_cloudy_day;
            case "partly-cloudy-night":
                return R.drawable.ic_partly_cloudy_night;
            default:
                return 0;
        }
    }

    /**
     * Picks which weather image to be set as background based on the icon string received from the API.
     *
     * @param icon
     * @return
     */
    @DrawableRes
    public static int pickWeatherBackgroundImage(String icon) {
        switch (icon) {
            case "clear-day":
                return R.drawable.back_image_clear_day;
            case "clear-night":
                return R.drawable.back_image_clear_night;
           /* case "rain":
                return R.drawable.back_image_rain;
            case "snow":
                return R.drawable.back_image_snow;
            case "sleet":
                return R.drawable.back_image_sleet;
            case "wind":
                return R.drawable.back_image_wind;
            case "fog":
                return R.drawable.back_image_fog;
            case "cloudy":
                return R.drawable.back_image_cloudy;
            case "partly-cloudy-day":
                return R.drawable.back_image_partly_cloudy_day;
            case "partly-cloudy-night":
                return R.drawable.back_image_;*/
            default:
                return 0;
        }
    }
}

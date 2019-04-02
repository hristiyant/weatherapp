package com.hristiyantodorov.weatherapp.util;

import android.support.annotation.DrawableRes;

import com.hristiyantodorov.weatherapp.R;

import static com.hristiyantodorov.weatherapp.util.Constants.ICON_CLEAR_DAY;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_CLEAR_NIGHT;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_CLOUDY;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_FOG;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_PARTLY_CLOUDY_DAY;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_PARTLY_CLOUDY_NIGHT;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_RAIN;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_SLEET;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_SNOW;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_WIND;

public class WeatherIconPickerUtil {

    /**
     * Picks which weather icon to be displayed based on the icon string received from the API.
     *
     * @param icon - "icon" String from API response
     * @return int - id of the drawable that should be displayed
     */
    @DrawableRes
    public static int pickWeatherIcon(String icon) {
        switch (icon) {
            case ICON_CLEAR_DAY:
                return R.drawable.ic_clear_day;
            case ICON_CLEAR_NIGHT:
                return R.drawable.ic_clear_night;
            case ICON_RAIN:
                return R.drawable.ic_rain;
            case ICON_SNOW:
                return R.drawable.ic_snow;
            case ICON_SLEET:
                return R.drawable.ic_sleet;
            case ICON_WIND:
                return R.drawable.ic_wind;
            case ICON_FOG:
                return R.drawable.ic_fog;
            case ICON_CLOUDY:
                return R.drawable.ic_cloudy;
            case ICON_PARTLY_CLOUDY_DAY:
                return R.drawable.ic_partly_cloudy_day;
            case ICON_PARTLY_CLOUDY_NIGHT:
                return R.drawable.ic_partly_cloudy_night;
            default:
                return 0;
        }
    }

    /**
     * Picks a background image based on the icon string received from the API.
     *
     * @param icon - "icon" String from API response
     * @return int - id of the drawable that should be displayed
     */
    @DrawableRes
    public static int pickWeatherBackgroundImage(String icon) {
        switch (icon) {
            case ICON_CLEAR_DAY:
                return R.drawable.back_image_clear_day;
            case ICON_CLEAR_NIGHT:
                return R.drawable.back_image_clear_night;
            case ICON_RAIN:
                return R.drawable.back_image_rain;
            case ICON_SNOW:
                return R.drawable.back_image_snow;
            case ICON_SLEET:
                return R.drawable.back_image_sleet;
            case ICON_WIND:
                return R.drawable.back_image_wind;
            case ICON_FOG:
                return R.drawable.back_image_fog;
            case ICON_CLOUDY:
                return R.drawable.back_image_cloudy;
            case ICON_PARTLY_CLOUDY_DAY:
                return R.drawable.back_image_partly_cloudy_day;
            case ICON_PARTLY_CLOUDY_NIGHT:
                return R.drawable.back_image_partly_cloudy_night;
            default:
                return 0;
        }
    }
}
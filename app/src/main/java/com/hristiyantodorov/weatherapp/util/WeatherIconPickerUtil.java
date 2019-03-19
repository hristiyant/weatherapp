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
            case Constants.ICON_CLEAR_DAY:
                return R.drawable.ic_clear_day;
            case Constants.ICON_CLEAR_NIGHT:
                return R.drawable.ic_clear_night;
            case Constants.ICON_RAIN:
                return R.drawable.ic_rain;
            case Constants.ICON_SNOW:
                return R.drawable.ic_snow;
            case Constants.ICON_SLEET:
                return R.drawable.ic_sleet;
            case Constants.ICON_WIND:
                return R.drawable.ic_wind;
            case Constants.ICON_FOG:
                return R.drawable.ic_fog;
            case Constants.ICON_CLOUDY:
                return R.drawable.ic_cloudy;
            case Constants.ICON_PARTLY_CLOUDY_DAY:
                return R.drawable.ic_partly_cloudy_day;
            case Constants.ICON_PARTLY_CLOUDY_NIGHT:
                return R.drawable.ic_partly_cloudy_night;
            default:
                return 0;
        }
    }
}

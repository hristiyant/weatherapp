package com.hristiyantodorov.weatherapp.util;

import android.support.annotation.DrawableRes;

import com.hristiyantodorov.weatherapp.R;

import static com.hristiyantodorov.weatherapp.util.Constants.ICON_CLEAR_DAY;
import static com.hristiyantodorov.weatherapp.util.Constants.ICON_CLEAR_NIGHT;

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
            case ICON_CLEAR_DAY:
                return R.drawable.ic_clear_day;
            case ICON_CLEAR_NIGHT:
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

    /**
     * Picks which weather image to be set as background based on the icon string received from the API.
     *
     * @param icon
     * @return
     */
    @DrawableRes
    public static int pickWeatherBackgroundImage(String icon) {
        switch (icon) {
            case ICON_CLEAR_DAY:
                return R.drawable.back_image_clear_day;
            case ICON_CLEAR_NIGHT:
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

package com.hristiyantodorov.weatherapp.util;

import java.util.Locale;

/**
 * Contains methods used for converting weather data from API into the desired format.
 */
public class WeatherDataFormatterUtil {

    public static String convertFahrenheitToCelsius(double degreesFahrenheit) {
        return convertRoundedDoubleToString((5.0 / 9.0) * (degreesFahrenheit - 32.0));
    }

    public static String convertMphToMs(double milesPerHour) {
        return convertRoundedDoubleToString(milesPerHour * 1609.34 / 3600);
    }

    public static String convertRoundedDoubleToString(double doubleValue) {
        return String.format(Locale.getDefault(), "%.0f", doubleValue);
    }

    public static String convertDoubleToPercentage(double doubleValue) {
        return convertRoundedDoubleToString(doubleValue * 100);
    }

}

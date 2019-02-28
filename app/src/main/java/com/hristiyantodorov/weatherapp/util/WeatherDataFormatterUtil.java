package com.hristiyantodorov.weatherapp.util;

import java.util.Locale;

public class WeatherDataFormatterUtil {

    public static String convertFromFahrenheitToCelsius() {

        return null;
    }

    public static String convertFromMphToMs(double milesPerHour) {
        return convertRoundedDoubleToString(milesPerHour * 1609.34 / 3600);
    }

    public static String convertRoundedDoubleToString(double value) {
        return String.format(Locale.getDefault(),"%.2f", value);
    }
}

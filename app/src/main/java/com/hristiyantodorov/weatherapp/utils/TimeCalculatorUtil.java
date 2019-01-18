package com.hristiyantodorov.weatherapp.utils;

import android.util.Log;

import java.util.Calendar;

import static android.support.constraint.Constraints.TAG;

/**
 * @author Hristiyan Todorov
 * This class is used to extract a greeting
 * base on the current time of the day.
 */
public class TimeCalculatorUtil {

    Calendar calendar = Calendar.getInstance();
    private int currentTime = calendar.get(Calendar.HOUR_OF_DAY);

    public String getGreetingBasedOnCurrentTime() {
        if (currentTime >= 4 && currentTime < 11) {
            Log.d(TAG, "Good Morning!");
            return "Good Morning!";
        } else if (currentTime >= 11 && currentTime < 18) {
            Log.d(TAG, "Good Afternoon!");
            return "Good Afternoon!";
        } else {
            Log.d(TAG, "Good Evening!");
            return "Good Evening!";
        }
    }

}
package com.hristiyantodorov.weatherapp.utils;

import android.util.Log;

import com.hristiyantodorov.weatherapp.App;
import com.hristiyantodorov.weatherapp.R;

import java.util.Calendar;

import static android.support.constraint.Constraints.TAG;

/**
 * @author Hristiyan Todorov
 * This class is used to extract a greeting
 * base on the current time of the day.
 */
public class TimeCalculatorUtil {

    private static final int MORNING_START = 4;
    private static final int AFTERNOON_START = 11;
    private static final int EVENING_START = 18;

    Calendar calendar = Calendar.getInstance();
    private int currentTime = calendar.get(Calendar.HOUR_OF_DAY);

    public String getGreetingBasedOnCurrentTime() {
        if (currentTime >= MORNING_START && currentTime < AFTERNOON_START) {
            Log.d(TAG, "morning");
            return App.getInstance().getString(R.string.greeting_good_morning);
        } else if (currentTime >= AFTERNOON_START && currentTime < EVENING_START) {
            Log.d(TAG, "afternoon ");
            return App.getInstance().getString(R.string.greeting_good_afternoon);
        } else {
            Log.d(TAG, "evening");
            return App.getInstance().getString(R.string.greeting_good_evening);
        }
    }

}
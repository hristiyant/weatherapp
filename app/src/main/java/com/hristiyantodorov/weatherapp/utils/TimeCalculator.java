package com.hristiyantodorov.weatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeCalculator {

    Calendar rightNow = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat();
    String time = "Current Time: " + format.format(rightNow.getTime());
    String hours = format.format(rightNow.getTime());

// offset to add since we're not UTC

    long offset = rightNow.get(Calendar.ZONE_OFFSET) +
            rightNow.get(Calendar.DST_OFFSET);

    long sinceMidnight = (rightNow.getTimeInMillis() + offset) %
            (24 * 60 * 60 * 1000);
    public String getCurrentTime(){
        return time;
    }

    public String getCurrentHours(){
        String hours = getCurrentTime().substring(getCurrentTime().length() - 6, getCurrentTime().length() - 3);
        return hours;
    }
}
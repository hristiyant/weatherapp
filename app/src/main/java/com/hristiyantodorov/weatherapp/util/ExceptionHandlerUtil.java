package com.hristiyantodorov.weatherapp.util;

import android.util.Log;

public class ExceptionHandlerUtil {

    private static final String TAG = "EHUtil";

    public static void logStackTrace(Exception e){
        Log.d(TAG, Log.getStackTraceString(e));
    }
}
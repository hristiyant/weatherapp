package com.hristiyantodorov.weatherapp.ui;

import android.util.Log;
import android.widget.Toast;

import com.hristiyantodorov.weatherapp.App;

public class ExceptionHandlerUtil {

    private static final String TAG = "EHUtil";

    public static void throwException(Exception e){
        Toast.makeText(App.getInstance().getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
    }

    public static void logStackTrace(Exception e){
        Log.d(TAG, Log.getStackTraceString(e));
    }
}

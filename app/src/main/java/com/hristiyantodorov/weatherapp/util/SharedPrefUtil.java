package com.hristiyantodorov.weatherapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {

    public static final String LOGGED_USER = "LOGGED_USER";

    private static SharedPreferences sharedPref;

    private SharedPrefUtil() {
    }

    public static void init(Context context) {
        if (sharedPref == null) {
            sharedPref =
                    context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
    }

    public static String read(String key, String defValue) {
        return sharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static boolean read(String key, boolean defValue) {
        return sharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public static Integer read(String key, int defValue) {
        return sharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        prefsEditor.putInt(key, value).apply();
    }

    public static SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    public static double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }
}
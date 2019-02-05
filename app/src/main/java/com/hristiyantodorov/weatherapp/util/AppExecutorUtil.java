package com.hristiyantodorov.weatherapp.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutorUtil {

    private static Executor executor = null;

    public static Executor getInstance() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor();
        }
        return executor;
    }
}

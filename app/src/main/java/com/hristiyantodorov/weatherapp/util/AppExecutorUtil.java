package com.hristiyantodorov.weatherapp.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutorUtil {

    private static Executor executor = null;
    private static final Object LOCK = new Object();

    public synchronized static Executor getInstance() {
        if (executor == null) {
            synchronized (LOCK) {
                if (executor == null) {
                    executor = Executors.newSingleThreadExecutor();
                }
            }
        }
        return executor;
    }
}

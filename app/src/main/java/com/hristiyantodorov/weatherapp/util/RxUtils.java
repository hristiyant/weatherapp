package com.hristiyantodorov.weatherapp.util;

import io.reactivex.CompletableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    public static <T> SingleTransformer<T, T> applySingleSchedulers() {
        return upstream -> upstream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static CompletableTransformer applyCompletableSchedulers() {
        return completable -> completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

package com.hristiyantodorov.weatherapp.util;

import com.jakewharton.rxbinding2.InitialValueObservable;

import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
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

    public static <T> ObservableTransformer<T, T> applyInitialValueObservable() {
        return observable -> ((InitialValueObservable) observable)
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }
}

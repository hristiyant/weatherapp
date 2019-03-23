package com.hristiyantodorov.weatherapp.presenter;

import com.hristiyantodorov.weatherapp.util.RxUtils;

import io.reactivex.Single;
import io.reactivex.SingleObserver;

public abstract class BasePresenter {

    /**
     * Subscribe a @Single observable that is emitted only once
     * <p>
     * Service is unregistered automatically when one of its method is called
     *
     * @param singleObservable single observable to subscribe
     * @param singleObserver   consumer with onSuccess(), onError() and onSubscribe() methods
     * @param <T>              return type of onSuccess()
     */
    protected  <T> void subscribeSingle(Single<T> singleObservable, SingleObserver<T> singleObserver) {
        singleObservable.compose(RxUtils.applySingleSchedulers()).subscribe(singleObserver);
    }
}

package com.hristiyantodorov.weatherapp.util;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class DisposableManager {

    private CompositeDisposable compositeDisposable;

    public DisposableManager() {
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Subscribe a @Single observable that is emitted only once
     * <p>
     * Service is unregistered automatically when one of its method is called
     */
    public <T> Disposable subscribeSingle(Single<T> singleObservable, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        return singleObservable.compose(RxUtils.applySingleSchedulers()).subscribe(onSuccess, onError);
    }

    public void subscribeCompletable(Completable completable, Action onComplete) {
        completable.compose(RxUtils.applyCompletableSchedulers()).doOnComplete(onComplete).subscribe();
    }

    public void subscribeCompletable(Completable completable) {
        completable.compose(RxUtils.applyCompletableSchedulers()).subscribe();
    }

    public void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        compositeDisposable.dispose();
    }
}

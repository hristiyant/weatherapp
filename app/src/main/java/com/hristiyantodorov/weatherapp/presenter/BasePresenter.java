package com.hristiyantodorov.weatherapp.presenter;

import com.hristiyantodorov.weatherapp.util.DisposableManager;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class BasePresenter {

    private DisposableManager disposableManager;

    protected BasePresenter() {
        disposableManager = new DisposableManager();
    }

    protected <T> Disposable subscribeSingle(Single<T> singleObservable, Consumer<T> onSuccess, Consumer<Throwable> onError) {
        return disposableManager.subscribeSingle(singleObservable, onSuccess, onError);
    }

    protected void subscribeCompletable(Completable completable) {
        disposableManager.subscribeCompletable(completable);
    }

    protected void subscribeCompletable(Completable completable, Action onComplete) {
        disposableManager.subscribeCompletable(completable, onComplete);
    }

    protected void addToCompositeDisposable(Disposable disposable) {
        disposableManager.add(disposable);
    }

    protected void clearDisposables() {
        disposableManager.dispose();
    }
}

package ru.granby.tabukan.model.business.interactor;

import android.util.Log;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import ru.granby.tabukan.model.data.cache.RxMemoryCache;
import ru.granby.tabukan.ui.base.BaseContract;


public abstract class BaseInteractor implements BaseContract.Interactor {
    private static final String TAG = "~BaseInteractor";
    protected RxMemoryCache cache;

    public BaseInteractor() {
        cache = new RxMemoryCache();
    }

    @Override
    public void clearCache() {
        cache.clear();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        cache.addDisposable(disposable);
    }

    protected <T> Completable set(String objectKey, T objectToSave, Completable saveCompletable) {
        return cache.set(objectKey, objectToSave, saveCompletable)
                .doOnComplete(() -> this.logSet(objectKey, objectToSave));
    }

    protected <T> Observable<T> get(String objectKey, Observable<T> getObservable) {
        return cache.get(objectKey, getObservable)
                .map((object) -> logGet(objectKey, object));
    }

    protected <T> Observable<T> getWithoutCaching(String objectKey, Observable<T> getObservable) {
        return getObservable.map((object) -> logGet(objectKey, object));
    }

    protected <T> Single<T> get(String objectKey, Single<T> getSingle) {
        return cache.get(objectKey, getSingle)
                .map((object) -> logGet(objectKey, object));
    }

    protected <T> Single<T> getWithoutCaching(String objectKey, Single<T> getSingle) {
        return getSingle.map((object) -> logGet(objectKey, object));
    }

    private <T> T logGet(String objectKey, T object) {
        Log.d(TAG, String.format("get(%s): %s", objectKey, object.toString()));
        return object;
    }

    private <T> void logSet(String objectKey, T object) {
        Log.d(TAG, String.format("set(%s): %s", objectKey, object.toString()));
    }
}

package ru.granby.tabukan.model.data.cache;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class RxMemoryCache {
    private static final String TAG = "~RxMemoryCache";
    private final Map<String, Object> cachedObjectsByKeys;
    private final CompositeDisposable compositeDisposable;

    public RxMemoryCache() {
        cachedObjectsByKeys = new HashMap<>();
        compositeDisposable = new CompositeDisposable();
    }

    public void clear() {
        compositeDisposable.dispose();
        cachedObjectsByKeys.clear();
    }

    public <T> Completable set(String objectKey, T objectToSave, Completable saveCompletable) {
        cachedObjectsByKeys.put(objectKey, objectToSave);
        return saveCompletable;
    }

    @SuppressWarnings("unchecked")
    public <T> Observable<T> get(String objectKey, Observable<T> getObservable) {
        if (cachedObjectsByKeys.containsKey(objectKey)) {
            return (Observable<T>) Observable.just(cachedObjectsByKeys.get(objectKey));
        } else {
            return getObservable
                    .flatMap((receivedObject) -> {
                        cachedObjectsByKeys.put(objectKey, receivedObject);
                        return Observable.just(receivedObject);
                    });
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Single<T> get(String objectKey, Single<T> getSingle) {
        if (cachedObjectsByKeys.containsKey(objectKey)) {
            return (Single<T>) Single.just(cachedObjectsByKeys.get(objectKey));
        } else {
            return getSingle
                    .flatMap((receivedObject) -> {
                        cachedObjectsByKeys.put(objectKey, receivedObject);
                        return Single.just(receivedObject);
                    });
        }
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}
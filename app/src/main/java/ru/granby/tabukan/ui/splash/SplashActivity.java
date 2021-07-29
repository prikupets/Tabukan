package ru.granby.tabukan.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.granby.tabukan.model.data.database.AppDatabaseManager;
import ru.granby.tabukan.model.data.sharedpreferences.SharedPreferencesManager;
import ru.granby.tabukan.ui.menu.MenuActivity;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "~SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //FIXME
        Completable.fromAction(() -> {
            AppDatabaseManager.getDatabase();
            SharedPreferencesManager.getInstance();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        startActivity(new Intent(SplashActivity.this, MenuActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: can't init application", e);
                    }
                });
    }
}
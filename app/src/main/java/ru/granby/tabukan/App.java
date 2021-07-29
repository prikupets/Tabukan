package ru.granby.tabukan;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

import lombok.Getter;
import ru.granby.tabukan.model.business.interactor.ApplicationInteractor;

public class App extends Application {
    private static final String TAG = "~App";

    @Getter
    private ApplicationInteractor interactor;

    @Getter
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(getApplicationContext(), initializationStatus -> { });
        interactor = new ApplicationInteractor();

        instance = this;
    }
}

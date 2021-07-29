package ru.granby.tabukan.model.business.interactor;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Collections;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import lombok.Synchronized;
import ru.granby.tabukan.App;
import ru.granby.tabukan.model.data.sharedpreferences.SharedPreferencesManager;
import ru.granby.tabukan.ui.menu.MenuContract;

import static ru.granby.tabukan.model.business.interactor.keys.MenuKey.FIRST_LAUNCH;
import static ru.granby.tabukan.model.business.interactor.keys.MenuKey.FIRST_LAUNCH_DEFAULT_VALUE;

public class MenuInteractor extends BaseInteractor implements MenuContract.Interactor {
    private static final String TAG = "~MenuInteractor";

    @Synchronized
    @Override
    public Single<AdRequest> getAdRequest() {
        return Single.fromCallable(() -> {
            RequestConfiguration configuration = new RequestConfiguration.Builder()
                    .setTestDeviceIds(Collections.singletonList(AdRequest.DEVICE_ID_EMULATOR))
                    .build();
            MobileAds.setRequestConfiguration(configuration);
            return new AdRequest.Builder().build();
        });
    }

    @Synchronized
    @Override
    public Single<Boolean> isMenuFirstLaunch() {
        return get(FIRST_LAUNCH, SharedPreferencesManager.getInstance()
                .getBoolean(FIRST_LAUNCH, FIRST_LAUNCH_DEFAULT_VALUE));
    }

    @Synchronized
    @Override
    public Completable setMenuFirstLaunch(boolean isFirstLaunch) {
        return set(FIRST_LAUNCH, isFirstLaunch, SharedPreferencesManager.getInstance()
                .putBoolean(FIRST_LAUNCH, isFirstLaunch));
    }

    @Synchronized
    @Override
    public Completable setDefaultCoinBalance() {
        return App.getInstance().getInteractor().setDefaultCoinBalance();
    }
}

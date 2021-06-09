package ru.granlovestea.forbiddenwords.ui.game;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

public class GamePresenter implements GameContract.Presenter {
    private GameContract.View view;

    @Override
    public void attach(GameContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        view = null;
        EventBus.getDefault().post(new DetachDeckViewPagerAdapterEvent());
    }

    @Override
    public void onNextCardClicked() {
        view.swipeNextCard();
    }

    @Override
    public void initAds() {
        RequestConfiguration configuration = new RequestConfiguration.Builder()
                .setTestDeviceIds(Collections.singletonList(AdRequest.DEVICE_ID_EMULATOR))
                .build();
        MobileAds.setRequestConfiguration(configuration);

        view.showAd();
    }
}

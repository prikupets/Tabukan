package ru.granby.tabukan.ui.multiplayer;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;

public class MultiplayerPresenter implements MultiplayerContract.Presenter {
    private MultiplayerContract.View view;

    @Override
    public void attach(MultiplayerContract.View view) {
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

    @Override
    public void onBackClicked() {
        view.finishView();
    }
}

package ru.granby.tabukan.ui.menu;

import com.google.android.gms.ads.AdRequest;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Synchronized;
import ru.granby.tabukan.ui.base.BaseContract;

public interface MenuContract {
    interface View extends BaseContract.View {
        void finishView();
        void showAdBanner(AdRequest adRequest);
        void showAboutAppDialog();
        void startMultiplayerActivity();
        void startSingleplayerActivity();
        void startStoreActivity();
        void showAdsAlreadyRemoved();
        void showBuyDisableAdsDialog();
        void showDisableAdsBought();
        void showDisableAdsPaymentError();
        void hideAds();
    }

    interface Presenter extends BaseContract.Presenter<View, Interactor> {
        void initAds();
        void onAboutAppClicked();
        void onPlayMultiplayerClicked();
        void onPlaySingleplayerClicked();
        void onStoreClicked();
        void onDisableAdsClicked();
        void onDisableAdsBought();
        void onDisableAdsPaymentError();
    }

    interface Interactor extends BaseContract.Interactor {
        Single<Boolean> isAdsEnabled();
        Completable setAdsEnabled(boolean state);
        Single<AdRequest> getAdRequest();
        Single<Boolean> isMenuFirstLaunch();
        Completable setMenuFirstLaunch(boolean state);
        Completable setDefaultCoinBalance();
    }
}

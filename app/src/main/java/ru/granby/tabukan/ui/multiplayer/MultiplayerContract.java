package ru.granby.tabukan.ui.multiplayer;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.granby.tabukan.model.data.database.relations.game.Card;
import ru.granby.tabukan.ui.base.BaseContract;

public interface MultiplayerContract {
    int CARDS_COUNT_TO_INTERSTITIAL_AD = 6;

    interface View extends BaseContract.View {
        void finishView();
        void hideAds();
        void loadInterstitialAd(InterstitialAdLoadCallback interstitialAdLoadCallback);
        void showAdBanner();
        void showInterstitialAd(InterstitialAd interstitialAd);
        void showCoinBalance(int coinBalance);
        void showCurrentLevel(int level);
        void showCardsLoadingError();
        void unsetViewPager();
        void showNoMoreLevelsDialog();
    }

    interface Presenter extends BaseContract.Presenter<View, Interactor>{
        Interactor getInteractor();
        void initAds();
        void onBackClicked();
        void onNextCardClicked();
        void showCoinBalance();
        void onGuideClicked();
        void showCurrentLevel();
    }

    interface Interactor extends BaseContract.Interactor {
        Single<Boolean> isAdsEnabled();
        Single<Integer> getCardsCountAfterInterstitialAd();
        Completable setCardsCountAfterInterstitialAd(int count);
        Completable setDefaultCardsCountAfterInterstitialAd();
        Single<Boolean> isMultiplayerFirstLaunch();
        Completable setMultiplayerFirstLaunch(boolean state);
        Single<Integer> getCoinBalance();
        Single<Integer> getCurrentCardIndex();
        Single<Integer> getCardsCount();
        Single<Integer> getMaxCardIndex();
        Completable setMaxCardIndex(int maxCardIndex);
    }
}

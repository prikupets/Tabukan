package ru.granby.tabukan.ui.multiplayer;

import com.google.android.gms.ads.AdRequest;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.granby.tabukan.model.data.database.relations.game.Card;
import ru.granby.tabukan.ui.base.BaseContract;

public interface MultiplayerContract {
    interface View extends BaseContract.View {
        void finishView();
        void showAdBanner(AdRequest adRequest);
        void hideAds();
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
        Single<Boolean> isMultiplayerFirstLaunch();
        Completable setMultiplayerFirstLaunch(boolean state);
        Single<AdRequest> getAdRequest();
        Single<Boolean> isAdsRemoved();
        Single<Integer> getCoinBalance();
        Completable setCurrentCardIndex(int currentCardIndex);
        Single<Integer> getCurrentCardIndex();
        Single<Card> getCardByIndex(int cardIndex);
        Single<Integer> getCardsCount();
    }
}

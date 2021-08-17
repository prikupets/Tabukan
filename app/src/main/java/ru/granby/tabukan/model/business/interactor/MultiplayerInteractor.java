package ru.granby.tabukan.model.business.interactor;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import lombok.Synchronized;
import ru.granby.tabukan.App;
import ru.granby.tabukan.model.data.database.AppDatabaseManager;
import ru.granby.tabukan.model.data.sharedpreferences.SharedPreferencesManager;
import ru.granby.tabukan.ui.multiplayer.MultiplayerContract;
import ru.granby.tabukan.ui.multiplayer.cards.CardsViewPagerContract;

import static ru.granby.tabukan.model.business.interactor.keys.MultiplayerKey.*;

public class MultiplayerInteractor extends BaseInteractor
        implements MultiplayerContract.Interactor, CardsViewPagerContract.Interactor {
    private static final String TAG = "~MultiplayerInteractor";

    @Override
    public Single<Boolean> isAdsEnabled() {
        return App.getInstance().getInteractor().isAdsEnabled();
    }

    @Override
    public Single<Integer> getCardsCountAfterInterstitialAd() {
        return get(CARDS_COUNT_AFTER_INTERSTITIAL_AD, SharedPreferencesManager.getInstance()
                .getInt(CARDS_COUNT_AFTER_INTERSTITIAL_AD, CARDS_COUNT_AFTER_INTERSTITIAL_AD_DEFAULT_VALUE));
    }

    @Override
    public Completable setCardsCountAfterInterstitialAd(int count) {
        return set(CARDS_COUNT_AFTER_INTERSTITIAL_AD, count, SharedPreferencesManager.getInstance()
                .putInt(CARDS_COUNT_AFTER_INTERSTITIAL_AD, count));
    }

    @Override
    public Completable setDefaultCardsCountAfterInterstitialAd() {
        return setCardsCountAfterInterstitialAd(CARDS_COUNT_AFTER_INTERSTITIAL_AD_DEFAULT_VALUE);
    }

    @Synchronized
    @Override
    public Single<Boolean> isMultiplayerFirstLaunch() {
        return get(FIRST_LAUNCH, SharedPreferencesManager.getInstance()
                .getBoolean(FIRST_LAUNCH, FIRST_LAUNCH_DEFAULT_VALUE));
    }

    @Synchronized
    @Override
    public Completable setMultiplayerFirstLaunch(boolean state) {
        return set(FIRST_LAUNCH, state, SharedPreferencesManager.getInstance()
                .putBoolean(FIRST_LAUNCH, state));
    }

    @Synchronized
    @Override
    public Single<Integer> getCoinBalance() {
        return App.getInstance().getInteractor().getCoinBalance();
    }

    @Synchronized
    @Override
    public Single<Integer> getCardsCount() {
        return get(CARDS_COUNT, AppDatabaseManager.getDatabase()
                .getCardDao().getCardsCount());
    }

    @Override
    public Single<Integer> getMaxCardIndex() {
        return get(MAX_CARD_INDEX, SharedPreferencesManager.getInstance()
                .getInt(MAX_CARD_INDEX, MAX_CARD_INDEX_DEFAULT_VALUE));
    }

    @Override
    public Completable setMaxCardIndex(int maxCardIndex) {
        return set(MAX_CARD_INDEX, maxCardIndex, SharedPreferencesManager.getInstance()
                .putInt(MAX_CARD_INDEX, maxCardIndex));
    }

    @Override
    public Single<Integer> getCurrentCardIndex() {
        return get(CURRENT_CARD_INDEX, SharedPreferencesManager.getInstance()
                .getInt(CURRENT_CARD_INDEX, CURRENT_CARD_INDEX_DEFAULT_VALUE));
    }

    @Override
    public Completable setCurrentCardIndex(int currentCardIndex) {
        return set(CURRENT_CARD_INDEX, currentCardIndex, SharedPreferencesManager.getInstance()
                .putInt(CURRENT_CARD_INDEX, currentCardIndex));
    }
}

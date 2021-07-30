package ru.granby.tabukan.model.business.interactor;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import lombok.Synchronized;
import ru.granby.tabukan.App;
import ru.granby.tabukan.model.data.database.AppDatabaseManager;
import ru.granby.tabukan.model.data.database.relations.game.Card;
import ru.granby.tabukan.model.data.sharedpreferences.SharedPreferencesManager;
import ru.granby.tabukan.ui.singleplayer.SingleplayerContract;

import static ru.granby.tabukan.model.business.interactor.keys.SingleplayerKey.*;

public class SingleplayerInteractor extends BaseInteractor implements SingleplayerContract.Interactor {
    private static final String TAG = "~SingleplayerInteractor";

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
    public Single<Boolean> isSingleplayerFirstLaunch() {
        return get(FIRST_LAUNCH, SharedPreferencesManager.getInstance()
                .getBoolean(FIRST_LAUNCH, FIRST_LAUNCH_DEFAULT_VALUE));
    }

    @Synchronized
    @Override
    public Completable setSingleplayerFirstLaunch(boolean state) {
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
    public Completable setCoinBalance(int coinBalance) {
        return App.getInstance().getInteractor().setCoinBalance(coinBalance);
    }

    @Synchronized
    @Override
    public Single<Integer> getCardsCount() {
        return get(CARDS_COUNT, AppDatabaseManager.getDatabase()
                .getCardDao().getCardsCount());
    }

    @Synchronized
    @Override
    public Single<Integer> getCurrentCardIndex() {
        return get(CURRENT_CARD_INDEX, SharedPreferencesManager.getInstance()
                .getInt(CURRENT_CARD_INDEX, CURRENT_CARD_INDEX_DEFAULT_VALUE));
    }

    @Synchronized
    @Override
    public Completable setCurrentCardIndex(int currentCardIndex) {
        return set(CURRENT_CARD_INDEX, currentCardIndex, SharedPreferencesManager.getInstance()
                .putInt(CURRENT_CARD_INDEX, currentCardIndex));
    }

    @Synchronized
    @Override
    public Single<Card> getCardByIndex(int cardIndex) {
        return getWithoutCaching(CARD, AppDatabaseManager.getDatabase()
                .getCardDao().getByIndex(cardIndex));
    }

    @Synchronized
    @Override
    public Single<Card> getCurrentCard() {
        return get(CURRENT_CARD,
                getCurrentCardIndex().flatMap(this::getCardByIndex)
        );
    }

    @Synchronized
    @Override
    public Completable setCurrentCard() {
        return getCurrentCardIndex()
                .flatMap(this::getCardByIndex)
                .flatMapCompletable(card -> set(CURRENT_CARD, card, Completable.complete()));

    }

    @Synchronized
    @Override
    public Single<Integer> getLastAssociationIndex() {
        return get(LAST_ASSOCIATION_INDEX, SharedPreferencesManager.getInstance()
                .getInt(LAST_ASSOCIATION_INDEX, LAST_ASSOCIATION_INDEX_DEFAULT_VALUE));
    }

    @Synchronized
    @Override
    public Completable setLastAssociationIndex(int lastAssociationIndex) {
        return set(LAST_ASSOCIATION_INDEX, lastAssociationIndex, SharedPreferencesManager.getInstance()
                .putInt(LAST_ASSOCIATION_INDEX, lastAssociationIndex));
    }

    @Synchronized
    @Override
    public Single<List<Character>> getCurrentWordLetters() {
        return get(CURRENT_WORD_LETTERS, SharedPreferencesManager.getInstance()
                .getObject(CURRENT_WORD_LETTERS, CURRENT_WORD_LETTERS_DEFAULT_VALUE,
                        new TypeToken<ArrayList<Character>>(){}.getType()));
    }

    @Synchronized
    @Override
    public Completable setCurrentWordLetters(List<Character> wordLetters) {
        return set(CURRENT_WORD_LETTERS, wordLetters, SharedPreferencesManager.getInstance()
                .putObject(CURRENT_WORD_LETTERS, wordLetters));
    }

    @Synchronized
    @Override
    public Single<List<Character>> getCurrentSelectLetters() {
        return get(CURRENT_SELECT_LETTERS, SharedPreferencesManager.getInstance()
                .getObject(CURRENT_SELECT_LETTERS, CURRENT_SELECT_LETTERS_DEFAULT_VALUE,
                        new TypeToken<ArrayList<Character>>(){}.getType()));
    }

    @Synchronized
    @Override
    public Completable setCurrentSelectLetters(List<Character> selectLetters) {
        return set(CURRENT_SELECT_LETTERS, selectLetters, SharedPreferencesManager.getInstance()
                .putObject(CURRENT_SELECT_LETTERS, selectLetters));
    }

    @Override
    public Single<Boolean> isAdsRemoved() {
        return App.getInstance().getInteractor().isAdsRemoved();
    }
}
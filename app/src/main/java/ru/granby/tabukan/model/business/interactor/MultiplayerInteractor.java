package ru.granby.tabukan.model.business.interactor;

import android.os.Parcel;

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
import ru.granby.tabukan.ui.multiplayer.MultiplayerContract;
import ru.granby.tabukan.ui.multiplayer.cards.CardsViewPagerContract;
import ru.granby.tabukan.ui.multiplayer.cards.card.CardContract;

import static ru.granby.tabukan.model.business.interactor.keys.MultiplayerKey.*;

public class MultiplayerInteractor extends BaseInteractor
        implements MultiplayerContract.Interactor, CardsViewPagerContract.Interactor {
    private static final String TAG = "~MultiplayerInteractor";

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
    public Single<AdRequest> getAdRequest() {
        return Single.fromCallable(() -> {
            RequestConfiguration configuration = new RequestConfiguration.Builder()
                    .setTestDeviceIds(Collections.singletonList(AdRequest.DEVICE_ID_EMULATOR))
                    .build();
            MobileAds.setRequestConfiguration(configuration);
            return new AdRequest.Builder().build();
        });
    }

    @Override
    public Single<Boolean> isAdsRemoved() {
        return App.getInstance().getInteractor().isAdsRemoved();
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
    public Single<Integer> getCurrentCardIndex() {
        return get(CURRENT_CARD_INDEX, SharedPreferencesManager.getInstance()
                .getInt(CURRENT_CARD_INDEX, CURRENT_CARD_INDEX_DEFAULT_VALUE));
    }

    @Override
    public Completable setCurrentCardIndex(int currentCardIndex) {
        return set(CURRENT_CARD_INDEX, currentCardIndex, SharedPreferencesManager.getInstance()
                .putInt(CURRENT_CARD_INDEX, currentCardIndex));
    }

    @Override
    public Single<Card> getCardByIndex(int cardIndex) {
        return getWithoutCaching(CARD, AppDatabaseManager.getDatabase()
                .getCardDao().getByIndex(cardIndex));
    }
}

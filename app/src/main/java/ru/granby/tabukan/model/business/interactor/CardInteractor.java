package ru.granby.tabukan.model.business.interactor;

import android.graphics.Bitmap;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.granby.tabukan.exception.ImageDownloadingException;
import ru.granby.tabukan.model.business.interactor.keys.MultiplayerKey;
import ru.granby.tabukan.model.data.database.AppDatabaseManager;
import ru.granby.tabukan.model.data.database.relations.game.Card;
import ru.granby.tabukan.model.data.sharedpreferences.SharedPreferencesManager;
import ru.granby.tabukan.ui.multiplayer.cards.card.CardContract;
import ru.granby.tabukan.utils.ImageDownloader;

import static ru.granby.tabukan.model.business.interactor.keys.MultiplayerKey.*;

public class CardInteractor extends BaseInteractor implements CardContract.Interactor {
    @Override
    public Single<Card> getCardByIndex(int cardIndex) {
        return getWithoutCaching(CARD, AppDatabaseManager.getDatabase().getCardDao()
                .getByIndex(cardIndex));
    }

    @Override
    public Single<Bitmap> getImageByUrl(String url) {
        return getWithoutCaching(CARD_IMAGE, ImageDownloader.download(url));
    }
}

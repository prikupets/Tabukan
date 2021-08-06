package ru.granby.tabukan.ui.multiplayer.cards.card;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.Synchronized;
import ru.granby.tabukan.model.data.database.entity.game.DbCard;
import ru.granby.tabukan.model.data.database.entity.game.DbDeck;
import ru.granby.tabukan.model.data.database.relations.game.Card;
import ru.granby.tabukan.ui.base.BaseContract;

public interface CardContract {
    int CARD_IMAGE_MIN_HEIGHT = 15;
    int MAX_TABU_WORDS_COUNT = 5;
    long CHANGE_CARD_IMAGE_ANIMATION_DURATION = 500;

    interface View extends BaseContract.View {
        void showCardTextData(Card card);
        void showCardImage(Bitmap image);
        void showTooltips();
        void hideTooltips();
    }

    interface Presenter extends BaseContract.Presenter<View, Interactor> {
        void setCardIndex(int cardIndex);
        void showCard();
    }

    interface Interactor extends BaseContract.Interactor, Serializable {
        Single<Card> getCardByIndex(int cardIndex);
        Single<Bitmap> getImageByUrl(String url);
    }
}


package ru.granby.tabukan.ui.multiplayer.cards;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import ru.granby.tabukan.model.data.database.relations.game.Card;
import ru.granby.tabukan.ui.base.BaseContract;
import ru.granby.tabukan.ui.multiplayer.cards.card.CardFragment;

public interface CardsViewPagerContract {
    interface View extends BaseContract.View {
        void showCard(int cardIndex);
        void setUp(int startPosition);
        void showSetUpError();
    }

    interface Presenter extends BaseContract.Presenter<View, Interactor> {
        CardFragment getCardFragment(int cardIndex);
        int getItemCount();
        void setUpWhenReady();
        void onPageSelected(int position);
    }

    interface Interactor extends BaseContract.Interactor {
        Single<Integer> getCardsCount();
        Completable setCurrentCardIndex(int cardIndex);
        Single<Integer> getCurrentCardIndex();
    }
}

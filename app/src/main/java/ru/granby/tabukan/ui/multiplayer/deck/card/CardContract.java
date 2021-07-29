package ru.granby.tabukan.ui.multiplayer.deck.card;

import ru.granby.tabukan.model.data.database.entity.game.DbCard;
import ru.granby.tabukan.model.data.database.entity.game.DbDeck;

public class CardContract {
    interface View {
        void setUpCard(DbCard dbCard);
    }

    interface Presenter {
        void attach(CardContract.View view);
        void detach();
        void setPosition(int position);
        void setDeck(DbDeck deck);
        void setUpCard();
    }
}


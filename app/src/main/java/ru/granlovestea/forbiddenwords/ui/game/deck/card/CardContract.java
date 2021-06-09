package ru.granlovestea.forbiddenwords.ui.game.deck.card;

import java.io.Serializable;

import ru.granlovestea.forbiddenwords.model.domain.Card;
import ru.granlovestea.forbiddenwords.model.domain.Deck;

public class CardContract {
    interface View {
        void setUpCard(Card card);
    }

    interface Presenter {
        void attach(CardContract.View view);
        void detach();
        void setPosition(int position);
        void setDeck(Deck deck);
        void setUpCard();
    }
}


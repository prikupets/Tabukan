package ru.granlovestea.forbiddenwords.ui.game.deck.card;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.granlovestea.forbiddenwords.model.dao.DeckDao;
import ru.granlovestea.forbiddenwords.model.domain.Card;
import ru.granlovestea.forbiddenwords.model.domain.Deck;
import ru.granlovestea.forbiddenwords.ui.game.deck.DeckChangedEvent;

public class CardPresenter implements CardContract.Presenter {
    private CardContract.View view;
    private Card card;
    private Deck deck;

    @Override
    public void attach(CardContract.View view) {
        this.view = view;
        EventBus.getDefault().register(this);
    }

    @Override
    public void detach() {
        this.view = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Override
    public void setPosition(int position) {
        card = deck.getCards().get(position);
    }

    @Override
    public void setUpCard() {
        view.setUpCard(card);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeckChangedEvent(DeckChangedEvent deckChangedEvent) {
        deck = deckChangedEvent.deck;
    }
}

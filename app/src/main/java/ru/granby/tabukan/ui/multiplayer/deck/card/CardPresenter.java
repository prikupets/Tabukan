package ru.granby.tabukan.ui.multiplayer.deck.card;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import kotlin.NotImplementedError;
import ru.granby.tabukan.model.data.database.entity.game.DbCard;
import ru.granby.tabukan.model.data.database.entity.game.DbDeck;
import ru.granby.tabukan.ui.multiplayer.deck.DeckChangedEvent;

public class CardPresenter implements CardContract.Presenter {
    private CardContract.View view;
    private DbCard dbCard;
    private DbDeck deck;

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
    public void setDeck(DbDeck deck) {
        this.deck = deck;
    }

    @Override
    public void setPosition(int position) {
        throw new NotImplementedError();
        //card = deck.getCards().get(position);
    }

    @Override
    public void setUpCard() {
        view.setUpCard(dbCard);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeckChangedEvent(DeckChangedEvent deckChangedEvent) {
        deck = deckChangedEvent.deck;
    }
}

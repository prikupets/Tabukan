package ru.granby.tabukan.ui.multiplayer.deck;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.granby.tabukan.model.data.database.entity.game.DbDeck;
import ru.granby.tabukan.ui.multiplayer.DetachDeckViewPagerAdapterEvent;
import ru.granby.tabukan.ui.multiplayer.deck.card.CardFragment;

public class DeckViewPagerPresenter implements DeckViewPagerContract.Presenter {
    private final int DEFAULT_DECK_INDEX = 0;
    private DeckViewPagerContract.View view;
    private int currentDeckIndex;
    private DbDeck deck;

    @Override
    public void attach(DeckViewPagerContract.View view) {
        this.view = view;
        setCurrentDeckIndex(DEFAULT_DECK_INDEX);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detach() {
        this.view = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public CardFragment getFragment(int position) {
        return CardFragment.newInstance(position, deck);
    }

    @Override
    public void setCurrentDeckIndex(int currentDeckIndex) {
        this.currentDeckIndex = currentDeckIndex;
        // FIXME
//        Deck deck = new DeckDao().getDeckByIndex(currentDeckIndex);
//        deck.shuffle();
//        this.deck = deck;

//        EventBus.getDefault().post(new DeckChangedEvent(deck));
    }

    @Override
    public int getItemsCount() {
        // FIXME
        return 0;
        //return deck.getCards().size();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDetachDeckViewPagerAdapterEvent(DetachDeckViewPagerAdapterEvent event) {
        detach();
    }
}
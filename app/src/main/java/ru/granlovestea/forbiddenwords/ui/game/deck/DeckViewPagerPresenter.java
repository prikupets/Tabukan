package ru.granlovestea.forbiddenwords.ui.game.deck;

import androidx.viewpager2.widget.ViewPager2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.granlovestea.forbiddenwords.model.dao.DeckDao;
import ru.granlovestea.forbiddenwords.model.domain.Deck;
import ru.granlovestea.forbiddenwords.ui.game.DetachDeckViewPagerAdapterEvent;
import ru.granlovestea.forbiddenwords.ui.game.deck.card.CardFragment;
import ru.granlovestea.forbiddenwords.ui.game.deck.pagetransformer.VerticalFlip;

public class DeckViewPagerPresenter implements DeckViewPagerContract.Presenter {
    private final int DEFAULT_DECK_INDEX = 0;
    private DeckViewPagerContract.View view;
    private int currentDeckIndex;
    private Deck deck;

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
        Deck deck = new DeckDao().getDeckByIndex(currentDeckIndex);
        deck.shuffle();
        this.deck = deck;

        EventBus.getDefault().post(new DeckChangedEvent(deck));
    }

    @Override
    public int getItemsCount() {
        return deck.getCards().size();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDetachDeckViewPagerAdapterEvent(DetachDeckViewPagerAdapterEvent event) {
        detach();
    }
}

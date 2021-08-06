package ru.granby.tabukan.ui.multiplayer.cards;

import android.annotation.SuppressLint;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.granby.tabukan.model.business.dto.game.CardsCountAndCardIndex;
import ru.granby.tabukan.ui.base.BasePresenter;
import ru.granby.tabukan.ui.multiplayer.cards.card.CardFragment;
import ru.granby.tabukan.ui.multiplayer.event.NewCardIndexEvent;
import ru.granby.tabukan.ui.multiplayer.event.UnbindCardsViewPagerEvent;

@SuppressLint("LongLogTag")
public class CardsViewPagerPresenter extends BasePresenter<CardsViewPagerContract.View, CardsViewPagerContract.Interactor> implements CardsViewPagerContract.Presenter {
    private static final String TAG = "~CardsViewPagerPresenter";
    private int cardsCount;

    @Override
    public void bind(CardsViewPagerContract.View view, CardsViewPagerContract.Interactor interactor) {
        super.bind(view, interactor);
        EventBus.getDefault().register(this);
        this.interactor = interactor;
    }

    @Override
    public void unbind() {
        super.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public CardFragment getCardFragment(int cardIndex) {
        return CardFragment.newInstance(cardIndex);
    }

    @Override
    public int getItemCount() {
        return cardsCount;
    }

    @Override
    public void setUpWhenReady() {
        interactor.addDisposable(
                Single.zip(
                        interactor.getCardsCount(),
                        interactor.getCurrentCardIndex(),
                        CardsCountAndCardIndex::new
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dto -> {
                    this.cardsCount = dto.getCardsCount();
                    return dto.getCardIndex();
                })
                .subscribe(view::setUp,
                        throwable -> {
                            Log.e(TAG, "Can't setUpWhenReady", throwable);
                            view.showSetUpError();
                        })
        );
    }

    @Override
    public void onPageSelected(int position) {
        EventBus.getDefault().post(new NewCardIndexEvent(position));
    }

    @Subscribe
    public void onNewCardIndexEvent(NewCardIndexEvent event) {
        view.showCard(event.getCardIndex());

        interactor.addDisposable(
                interactor.setCurrentCardIndex(event.getCardIndex())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {},
                        t -> Log.e(TAG, "onNewCardIndexEvent: Can't setCurrentCardIndex", t)
                )
        );
    }

    @Subscribe
    public void onUnbindCardsEvent (UnbindCardsViewPagerEvent event) {
        unbind();
    }
}
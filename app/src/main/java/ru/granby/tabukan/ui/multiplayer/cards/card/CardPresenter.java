package ru.granby.tabukan.ui.multiplayer.cards.card;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.granby.tabukan.exception.ImageDownloadingException;
import ru.granby.tabukan.ui.base.BasePresenter;
import ru.granby.tabukan.ui.multiplayer.event.CardsLoadingErrorEvent;
import ru.granby.tabukan.ui.multiplayer.event.HideTooltipsEvent;
import ru.granby.tabukan.ui.multiplayer.event.ShowTooltipsEvent;

public class CardPresenter extends BasePresenter<CardContract.View, CardContract.Interactor> implements CardContract.Presenter {
    private static final String TAG = "~CardPresenter";
    private int cardIndex;

    @Override
    public void bind(CardContract.View view, CardContract.Interactor interactor) {
        super.bind(view, interactor);
        EventBus.getDefault().register(this);
    }

    @Override
    public void unbind() {
        super.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    @Override
    public void showCard() {
        interactor.addDisposable(
                interactor.getCardByIndex(cardIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .map(card -> {
                        view.showCardTextData(card);
                        return card.getHeader().getDbHeader().getImageUrl();
                    })
                .observeOn(Schedulers.io())
                    .flatMap(interactor::getImageByUrl)
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            view::showCardImage,
                            throwable -> {
                                if(throwable instanceof ImageDownloadingException) {
                                    Log.w(TAG, "showCard: can't download image", throwable);
                                } else {
                                    Log.e(TAG, "showCard: unexpected exception", throwable);
                                }
                            }
                    )
        );
    }

    @Subscribe
    public void onShowTooltipsEvent(ShowTooltipsEvent event) {
        view.showTooltips();
    }

    @Subscribe
    public void onHideTooltipsEvent(HideTooltipsEvent event) {
        view.hideTooltips();
    }
}

package ru.granby.tabukan.ui.multiplayer;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.granby.tabukan.exception.AdsRemovedException;
import ru.granby.tabukan.exception.IncorrectCardIndexException;
import ru.granby.tabukan.model.business.dto.game.CardsCountAndCardIndex;
import ru.granby.tabukan.model.business.helpers.CardIndexHelper;
import ru.granby.tabukan.ui.base.BasePresenter;
import ru.granby.tabukan.ui.multiplayer.event.CardsLoadingErrorEvent;
import ru.granby.tabukan.ui.multiplayer.event.UnbindCardsViewPagerEvent;
import ru.granby.tabukan.ui.multiplayer.event.HideTooltipsEvent;
import ru.granby.tabukan.ui.multiplayer.event.ShowTooltipsEvent;
import ru.granby.tabukan.ui.multiplayer.event.NewCardIndexEvent;
import ru.granby.tabukan.ui.singleplayer.SingleplayerContract;

public class MultiplayerPresenter extends BasePresenter<MultiplayerContract.View, MultiplayerContract.Interactor> implements MultiplayerContract.Presenter {
    private static final String TAG = "~MultiplayerPresenter";

    @Override
    public void bind(MultiplayerContract.View view, MultiplayerContract.Interactor interactor) {
        super.bind(view, interactor);
        EventBus.getDefault().register(this);

        interactor.addDisposable(
                interactor.isMultiplayerFirstLaunch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .map(result -> {
                        if(result) showTempTooltips();
                        return result;
                    })
                .observeOn(Schedulers.io())
                    .flatMapCompletable(result -> result ? interactor.setMultiplayerFirstLaunch(false) : Completable.complete())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {},
                            throwable -> Log.e(TAG, "can't isSingleplayerFirstLaunch: ", throwable)
                    )
        );
    }

    @Override
    public void unbind() {
        EventBus.getDefault().post(new UnbindCardsViewPagerEvent());
        EventBus.getDefault().unregister(this);
        view.unsetViewPager();
        super.unbind();
    }

    @Override
    public MultiplayerContract.Interactor getInteractor() {
        return interactor;
    }

    @Override
    public void initAds() {
        interactor.addDisposable(
                interactor.isAdsRemoved()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(adsRemoved -> {
                        if(adsRemoved) {
                            view.hideAds();
                            throw new AdsRemovedException("Ads removed, can't hide it");
                        }
                        return Single.just(adsRemoved);
                    })
                .observeOn(Schedulers.io())
                    .flatMap(ignored -> interactor.getAdRequest())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            view::showAdBanner,
                            throwable -> {
                                if(!(throwable instanceof AdsRemovedException))
                                    Log.e(TAG, "can't initAds: ", throwable);
                            }
                    )
        );
    }

    @Override
    public void onBackClicked() {
        view.finishView();
    }

    @Override
    public void showCoinBalance() {
        interactor.addDisposable(
                interactor.getCoinBalance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            view::showCoinBalance,
                            (throwable) -> Log.e(TAG, "can't showCoinBalance: ", throwable)
                    )
        );
    }

    @Override
    public void onGuideClicked() {
        showTempTooltips();
    }

    @Override
    public void showCurrentLevel() {
        interactor.addDisposable(
                interactor.getCurrentCardIndex()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            cardIndex -> view.showCurrentLevel(cardIndex + 1),
                            (throwable) -> {
                                Log.e(TAG, "showCurrentLevel: can't getCurrentCardIndex()");
                                view.showCardsLoadingError();
                            }));
    }

    @Override
    public void onNextCardClicked() {
        interactor.addDisposable(
                Single.zip(
                        interactor.getCardsCount(),
                        interactor.getCurrentCardIndex(),
                        CardsCountAndCardIndex::new
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .map(dto -> CardIndexHelper.changeIndexTo(
                            dto.getCardIndex() + 1, dto.getCardsCount()))
                    .subscribe(
                            cardIndex -> EventBus.getDefault().post(new NewCardIndexEvent(cardIndex)),
                            throwable -> {
                                if(throwable instanceof IncorrectCardIndexException) {
                                    Log.i(TAG, "onNextCardClicked: incorrect card index");
                                    view.showNoMoreLevelsDialog();
                                } else {
                                    Log.e(TAG, "can't go to next card", throwable);
                                }
                            })
        );
    }

    @Subscribe
    public void onNewCardIndexEvent(NewCardIndexEvent event) {
        view.showCurrentLevel(event.getCardIndex() + 1);
    }

    @Subscribe
    public void onCardsLoadingErrorEvent(CardsLoadingErrorEvent event) {
        view.showCardsLoadingError();
    }

    private void showTempTooltips() {
        EventBus.getDefault().post(new ShowTooltipsEvent());

        interactor.addDisposable(
                Completable.complete()
                .delay(SingleplayerContract.TEMP_TOOLTIPS_DURATION, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> EventBus.getDefault().post(new HideTooltipsEvent())));
    }
}

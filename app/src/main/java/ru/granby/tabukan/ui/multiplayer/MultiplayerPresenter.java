package ru.granby.tabukan.ui.multiplayer;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.granby.tabukan.exception.AdsDisabledException;
import ru.granby.tabukan.exception.CardAlreadySeenException;
import ru.granby.tabukan.exception.IncorrectCardIndexException;
import ru.granby.tabukan.model.business.dto.game.CardsCountAndCardIndex;
import ru.granby.tabukan.model.business.dto.game.CurrentCardIndexAndMaxCardIndexAndCardsCountAfterInterstitialAd;
import ru.granby.tabukan.model.business.helpers.CardIndexHelper;
import ru.granby.tabukan.ui.base.BasePresenter;
import ru.granby.tabukan.ui.multiplayer.event.CardsLoadingErrorEvent;
import ru.granby.tabukan.ui.multiplayer.event.FirstCardShownEvent;
import ru.granby.tabukan.ui.multiplayer.event.HideTooltipsEvent;
import ru.granby.tabukan.ui.multiplayer.event.NewCardIndexEvent;
import ru.granby.tabukan.ui.multiplayer.event.ShowTooltipsEvent;
import ru.granby.tabukan.ui.multiplayer.event.UnbindCardsViewPagerEvent;

import static ru.granby.tabukan.ui.multiplayer.MultiplayerContract.CARDS_COUNT_TO_INTERSTITIAL_AD;

public class MultiplayerPresenter extends BasePresenter<MultiplayerContract.View, MultiplayerContract.Interactor> implements MultiplayerContract.Presenter {
    private static final String TAG = "~MultiplayerPresenter";
    private static final long TEMP_TOOLTIPS_DURATION = 3000; // ms
    private int lastCardIndex = -1;
    private int firstCardIndex = -2;

    @Nullable
    private InterstitialAd interstitialAd;

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
                            throwable -> Log.e(TAG, "isMultiplayerFirstLaunch: ", throwable)
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
    public void initAds() {
        interactor.addDisposable(
                interactor.isAdsEnabled()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .flatMapCompletable(adsEnabled -> {
                        if(adsEnabled) {
                            return Completable.complete();
                        } else {
                            view.hideAds();
                            throw new AdsDisabledException("Ads disabled - shouldn't show it");
                        }
                    })
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                initAdsConfiguration();
                                view.showAdBanner();
                            },
                            throwable -> {
                                if(!(throwable instanceof AdsDisabledException))
                                    Log.e(TAG, "can't initAds: ", throwable);
                            }
                    )
        );
    }

    @Override
    public MultiplayerContract.Interactor getInteractor() {
        return interactor;
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
                            throwable -> Log.e(TAG, "can't showCoinBalance: ", throwable)
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
                            throwable -> {
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

        doIfAdsEnabled(() -> {
            synchronized (this) {
                if(lastCardIndex == event.getCardIndex() || lastCardIndex == firstCardIndex) {
                    return;
                }
                lastCardIndex = event.getCardIndex();

                interactor.addDisposable(
                        Single.zip(
                                interactor.getCurrentCardIndex(),
                                interactor.getMaxCardIndex(),
                                interactor.getCardsCountAfterInterstitialAd(),
                                CurrentCardIndexAndMaxCardIndexAndCardsCountAfterInterstitialAd::new
                        )
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .flatMapCompletable(dto -> {
                                    if(dto.getCurrentCardIndex() > dto.getMaxCardIndex()) {
                                        return Completable.mergeArray(
                                                interactor.setMaxCardIndex(dto.getMaxCardIndex() + 1),
                                                interactor.setCardsCountAfterInterstitialAd(dto.getCardsCountAfterInterstitialAd() + 1)
                                        );
                                    }
                                    throw new CardAlreadySeenException("maxCardIndex > currentCardIndex: shouldn't consider it for interstitial ads");
                                })
                                .andThen(interactor.getCardsCountAfterInterstitialAd())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(cardsCountAfterInterstitialAd -> {
                                            if (cardsCountAfterInterstitialAd >= CARDS_COUNT_TO_INTERSTITIAL_AD) {
                                                tryShowInterstitialAd();
                                            } else {
                                                if (interstitialAd == null) {
                                                    loadInterstitialAd(false);
                                                }
                                            }
                                        },
                                        throwable -> {
                                            if(!(throwable instanceof CardAlreadySeenException)) {
                                                Log.e(TAG, "onNewCardIndexEvent: getCardsCountAfterInterstitialAd error");
                                            }
                                        })
                );
            }
        });

    }

    @Subscribe
    public void onCardsLoadingErrorEvent(CardsLoadingErrorEvent event) {
        view.showCardsLoadingError();
    }

    @Subscribe
    public void onFirstCardShownEvent(FirstCardShownEvent event) {
        firstCardIndex = event.getCardIndex();
    }

    private void showTempTooltips() {
        EventBus.getDefault().post(new ShowTooltipsEvent());

        interactor.addDisposable(
                Completable.complete()
                .delay(TEMP_TOOLTIPS_DURATION, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> EventBus.getDefault().post(new HideTooltipsEvent())));
    }

    private void initAdsConfiguration() {
        RequestConfiguration configuration = new RequestConfiguration.Builder()
                .setTestDeviceIds(Collections.singletonList(AdRequest.DEVICE_ID_EMULATOR))
                .build();
        MobileAds.setRequestConfiguration(configuration);
    }

    private void loadInterstitialAd(boolean showOnLoad) {
        view.loadInterstitialAd(new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd loadedInterstitialAd) {
                Log.i(TAG, "loadInterstitialAd: onAdLoaded");
                interstitialAd = loadedInterstitialAd;
                if(showOnLoad) {
                    tryShowInterstitialAd();
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.w(TAG, "loadInterstitialAd: onAdFailedToLoad: " + loadAdError.getMessage());
                interstitialAd = null;
            }
        });
    }

    private void tryShowInterstitialAd() {
        if(interstitialAd == null) {
            loadInterstitialAd(true);
        } else {
            resetCardsCountAfterInterstitialAds();
            view.showInterstitialAd(interstitialAd);
            interstitialAd = null;
        }
    }

    private void resetCardsCountAfterInterstitialAds() {
        interactor.addDisposable(
                interactor.setDefaultCardsCountAfterInterstitialAd()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {},
                            throwable -> Log.e(TAG, "Can't resetCardsCountAfterInterstitialAds", throwable))
        );
    }

    private void doIfAdsEnabled(Action action) {
        interactor.addDisposable(
                interactor.isAdsEnabled()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(adsEnabled -> {
                        if(adsEnabled) {
                            action.run();
                        }
                    }, throwable -> Log.e(TAG, "doIfAdsEnabled: ", throwable))
        );
    }
}

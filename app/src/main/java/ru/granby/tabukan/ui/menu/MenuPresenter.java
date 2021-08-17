package ru.granby.tabukan.ui.menu;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.granby.tabukan.exception.AdsAlreadyDisabledException;
import ru.granby.tabukan.exception.AdsDisabledException;
import ru.granby.tabukan.ui.base.BasePresenter;

public class MenuPresenter extends BasePresenter<MenuContract.View, MenuContract.Interactor> implements MenuContract.Presenter {
    private final String TAG = "~MenuPresenter";

    @Override
    public void bind(MenuContract.View view, MenuContract.Interactor interactor) {
        super.bind(view, interactor);

        interactor.addDisposable(
                interactor.isMenuFirstLaunch()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                    .flatMapCompletable(isFirstLaunch -> {
                        if(isFirstLaunch) {
                            return interactor.setDefaultCoinBalance()
                                    .andThen(interactor.setMenuFirstLaunch(false));
                        }

                        return Completable.complete();
                    })
                    .subscribe(() -> {},
                            (throwable -> Log.e(TAG, "bind: can't get/save isMenuFirstLaunch"))
                    )
        );
    }

    @Override
    public void initAds() {
        interactor.addDisposable(
                interactor.isAdsEnabled()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(adsEnabled -> {
                            if(adsEnabled) {
                                return Single.just(adsEnabled);
                            } else {
                                view.hideAds();
                                throw new AdsDisabledException("Ads disabled - shouldn't show it");
                            }
                        })
                    .observeOn(Schedulers.io())
                        .flatMap(ignored -> interactor.getAdRequest())
                    .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                view::showAdBanner,
                                throwable -> {
                                    if(!(throwable instanceof AdsDisabledException))
                                        Log.e(TAG, "can't initAds: ", throwable);
                                }
                        )
        );
    }

    @Override
    public void onAboutAppClicked() {
        view.showAboutAppDialog();
    }

    @Override
    public void onPlayMultiplayerClicked() {
        view.startMultiplayerActivity();
    }

    @Override
    public void onPlaySingleplayerClicked() {
        view.startSingleplayerActivity();
    }

    @Override
    public void onStoreClicked() {
        view.startStoreActivity();
    }

    @Override
    public void onDisableAdsClicked() {
        interactor.addDisposable(
                interactor.isAdsEnabled()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                    .flatMapCompletable(adsEnabled -> {
                        if (adsEnabled) {
                            return Completable.complete();
                        } else {
                            throw new AdsAlreadyDisabledException("Already disabled ads");
                        }
                    })
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        () -> view.showBuyDisableAdsDialog(),
                        throwable -> {
                            Log.e(TAG, "onRemoveAdsClicked: ", throwable);
                            if(throwable instanceof AdsAlreadyDisabledException) view.showAdsAlreadyRemoved();
                        })
        );
    }

    @Override
    public void onDisableAdsBought() {
        interactor.addDisposable(
                interactor.setAdsEnabled(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Log.i(TAG, "disableAds bought");
                        view.showDisableAdsBought();
                        view.hideAds();
                    })
        );
    }

    @Override
    public void onDisableAdsPaymentError() {
        view.showDisableAdsPaymentError();
    }
}

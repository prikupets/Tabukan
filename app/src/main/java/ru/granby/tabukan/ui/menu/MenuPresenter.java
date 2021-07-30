package ru.granby.tabukan.ui.menu;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.granby.tabukan.exception.AdsAlreadyRemovedException;
import ru.granby.tabukan.exception.AdsRemovedException;
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
    public void onPlayMultiplayerClicked() {
        view.startMultiplayerActivity();
    }

    @Override
    public void onPlaySingleplayerClicked() {
        view.startSingleplayerActivity();
    }

    @Override
    public void onRemoveAdsClicked() {
        interactor.addDisposable(
                interactor.isAdsRemoved()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                    .flatMapCompletable(adsRemoved -> {
                        if (adsRemoved) throw new AdsAlreadyRemovedException("Already removed ads");
                        else return Completable.complete();
                    })
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        () -> view.showBuyRemoveAdsDialog(),
                        (throwable) -> {
                            Log.e(TAG, "onRemoveAdsClicked: ", throwable);
                            if(throwable instanceof AdsAlreadyRemovedException) view.showAdsAlreadyRemoved();
                        })
        );
    }

    @Override
    public void onRemoveAdsBought() {
        interactor.addDisposable(
                interactor.setAdsRemoved(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Log.i(TAG, "removeAds bought");
                        view.showRemoveAdsBought();
                        view.hideAds();
                    })
        );
    }

    @Override
    public void onRemoveAdsPaymentError() {
        view.showRemoveAdsPaymentError();
    }
}

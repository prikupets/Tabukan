package ru.granby.tabukan.ui.menu;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                interactor.getAdRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(view::showAdBanner)
                    .doOnError((throwable -> Log.e(TAG, "can't getAdRequest: ", throwable)))
                .subscribe());
    }

    @Override
    public void onPlayMultiplayerClicked() {
        view.startMultiplayerActivity();
    }

    @Override
    public void onPlaySingleplayerClicked() {
        view.startSingleplayerActivity();
    }
}

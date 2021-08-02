package ru.granby.tabukan.ui.store;

import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.granby.tabukan.ui.base.BasePresenter;

public class StorePresenter extends BasePresenter<StoreContract.View, StoreContract.Interactor> implements StoreContract.Presenter {
    private final String TAG = "~StorePresenter";

    @Override
    public void bind(StoreContract.View view, StoreContract.Interactor interactor) {
        super.bind(view, interactor);
    }

    @Override
    public void onBackButtonClicked() {
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
    public void showRegionalPrices() {
        // TODO: google play IAP integration
        view.showRegionalPrices("100", "200", "300", "400");
    }

    @Override
    public void onCoinsPouchClicked() {
        //TODO
        Log.d(TAG, "onCoinsPouchClicked");
    }

    @Override
    public void onCoinsBagClicked() {
        //TODO
        Log.d(TAG, "onCoinsBagClicked");
    }

    @Override
    public void onChestClicked() {
        //TODO
        Log.d(TAG, "onChestClicked");
    }

    @Override
    public void onBigChestClicked() {
        //TODO
        Log.d(TAG, "onBigChestClicked");
    }

    @Override
    public void onCoinsForAdClicked() {
        // TODO: https://developers.google.com/admob/android/rewarded integration
    }
}

package ru.granby.tabukan.ui.store;

import com.google.android.gms.ads.AdRequest;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.granby.tabukan.ui.base.BaseContract;

public interface StoreContract {
    interface View extends BaseContract.View {
        void finishView();
        void showRegionalPrices(String coinsPouchPrice,
                                String coinsBagPrice,
                                String coinsChestPrice,
                                String coinsBigChestPrice);
        void showCoinBalance(int coinBalance);
    }

    interface Presenter extends BaseContract.Presenter<View, Interactor> {
        void onBackButtonClicked();
        void showCoinBalance();
        void showRegionalPrices();
        void onCoinsPouchClicked();
        void onCoinsBagClicked();
        void onChestClicked();
        void onBigChestClicked();
        void onCoinsForAdClicked();
    }

    interface Interactor extends BaseContract.Interactor {
        Single<Integer> getCoinBalance();
    }
}

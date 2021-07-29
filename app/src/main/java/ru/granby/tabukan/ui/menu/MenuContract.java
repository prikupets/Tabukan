package ru.granby.tabukan.ui.menu;

import com.google.android.gms.ads.AdRequest;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import lombok.Synchronized;
import ru.granby.tabukan.ui.base.BaseContract;

public interface MenuContract {
    interface View extends BaseContract.View {
        void showAdBanner(AdRequest adRequest);

        void setOnPlayMultiplayerClickedListener();
        void setOnPlaySingleplayerClickedListener();

        void startMultiplayerActivity();
        void startSingleplayerActivity();
    }

    interface Presenter extends BaseContract.Presenter<View, Interactor> {
        void initAds();

        void onPlayMultiplayerClicked();
        void onPlaySingleplayerClicked();
    }

    interface Interactor extends BaseContract.Interactor {
        Single<AdRequest> getAdRequest();
        Single<Boolean> isMenuFirstLaunch();
        Completable setMenuFirstLaunch(boolean isFirstLaunch);
        Completable setDefaultCoinBalance();
    }
}

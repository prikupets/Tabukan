package ru.granby.tabukan.model.business.interactor;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.granby.tabukan.App;
import ru.granby.tabukan.ui.store.StoreContract;

public class StoreInteractor extends BaseInteractor implements StoreContract.Interactor {
    @Override
    public Single<Integer> getCoinBalance() {
        return App.getInstance().getInteractor().getCoinBalance();
    }
}

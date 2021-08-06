package ru.granby.tabukan.ui.base;

import io.reactivex.rxjava3.disposables.Disposable;

public interface BaseContract {
    interface View {

    }

    interface Presenter<V extends View, I extends Interactor> {
        void bind(V view, I interactor);
        void unbind();
        boolean isViewBound();
    }

    interface Interactor {
        void clearCache();
        void addDisposable(Disposable disposable);
    }
}

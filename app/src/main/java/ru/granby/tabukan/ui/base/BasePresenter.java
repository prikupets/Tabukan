package ru.granby.tabukan.ui.base;

public class BasePresenter<V extends BaseContract.View, I extends BaseContract.Interactor> implements BaseContract.Presenter<V, I> {
    protected V view;
    protected I interactor;

    @Override
    public void bind(V view, I interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void unbind() {
        view = null;
        interactor.clearCache();
        interactor = null;
    }

    @Override
    public boolean isViewBound() {
        return view != null;
    }
}

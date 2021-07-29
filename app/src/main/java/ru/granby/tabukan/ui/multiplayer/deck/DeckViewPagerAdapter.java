package ru.granby.tabukan.ui.multiplayer.deck;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DeckViewPagerAdapter extends FragmentStateAdapter implements DeckViewPagerContract.View {
    private DeckViewPagerContract.Presenter presenter;

    public DeckViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        presenter = new DeckViewPagerPresenter();
        presenter.attach(this);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return presenter.getFragment(position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemsCount();
    }
}

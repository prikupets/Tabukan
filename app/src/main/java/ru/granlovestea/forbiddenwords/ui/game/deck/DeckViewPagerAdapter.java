package ru.granlovestea.forbiddenwords.ui.game.deck;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import ru.granlovestea.forbiddenwords.databinding.ActivityGameBinding;
import ru.granlovestea.forbiddenwords.ui.game.deck.pagetransformer.VerticalFlip;

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

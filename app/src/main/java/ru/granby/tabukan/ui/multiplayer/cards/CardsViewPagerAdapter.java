package ru.granby.tabukan.ui.multiplayer.cards;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import org.greenrobot.eventbus.Subscribe;

import ru.granby.tabukan.R;
import ru.granby.tabukan.ui.base.pagetransformer.HorizontalFlip;
import ru.granby.tabukan.utils.Toaster;

public class CardsViewPagerAdapter extends FragmentStateAdapter implements CardsViewPagerContract.View {
    public static final String TAG = "~DeckViewPagerAdapter";
    private CardsViewPagerContract.Presenter presenter;
    private ViewPager2 viewPager;

    public CardsViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ViewPager2 viewPager, CardsViewPagerContract.Interactor interactor) {
        super(fragmentActivity);
        this.viewPager = viewPager;

        presenter = new CardsViewPagerPresenter();
        presenter.bind(this, interactor);
    }

    @NonNull
    @Override
    public Fragment createFragment(int cardIndex) {
        return presenter.getCardFragment(cardIndex);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public void showCard(int cardIndex) {
        viewPager.setCurrentItem(cardIndex, true);
    }

    @Override
    public void setUp(int startPosition) {
        viewPager.setPageTransformer(new HorizontalFlip());

        viewPager.setAdapter(this);
        viewPager.setCurrentItem(startPosition, false);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                presenter.onPageSelected(position);
            }
        });
    }

    @Override
    public void showSetUpError() {
        Toaster.showLongToast(viewPager.getContext(),
                viewPager.getResources().getString(R.string.cards_loading_error));
    }

    public void setUpWhenReady() {
        presenter.setUpWhenReady();
    }
}

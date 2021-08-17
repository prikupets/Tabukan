package ru.granby.tabukan.ui.multiplayer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.greenrobot.eventbus.EventBus;

import ru.granby.tabukan.R;
import ru.granby.tabukan.databinding.MultiplayerActivityBinding;
import ru.granby.tabukan.model.business.interactor.MultiplayerInteractor;
import ru.granby.tabukan.ui.multiplayer.cards.CardsViewPagerAdapter;
import ru.granby.tabukan.ui.base.pagetransformer.HorizontalFlip;
import ru.granby.tabukan.ui.multiplayer.cards.CardsViewPagerContract;
import ru.granby.tabukan.utils.Toaster;

public class MultiplayerActivity extends AppCompatActivity implements MultiplayerContract.View {
    private static final String TAG = "~MultiplayerActivity";
    protected MultiplayerContract.Presenter presenter;
    private MultiplayerActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MultiplayerActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (presenter == null)
            presenter = new MultiplayerPresenter();
        presenter.bind(this, new MultiplayerInteractor());

        setUpViews();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!presenter.isViewBound()) {
            presenter.bind(this, new MultiplayerInteractor());
        }

        setUpViewPager();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbind();
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void loadInterstitialAd(InterstitialAdLoadCallback interstitialAdLoadCallback) {
        InterstitialAd.load(
                this,
                getResources().getString(R.string.interstitial_ad_unit_id),
                new AdRequest.Builder().build(),
                interstitialAdLoadCallback);
    }

    @Override
    public void showAdBanner() {
        Log.i(TAG, "Showing ad banner");
        binding.adBanner.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void showInterstitialAd(InterstitialAd interstitialAd) {
        Log.i(TAG, "Showing interstitial ad");
        interstitialAd.show(this);
    }

    @Override
    public void hideAds() {
        binding.multiplayerRoot.removeView(binding.adBanner);
    }

    @Override
    public void showCoinBalance(int coinBalance) {
        binding.coinsButtonText.setText(String.valueOf(coinBalance));
    }

    @Override
    public void showCurrentLevel(int level) {
        binding.levelButtonText.setText(String.valueOf(level));
    }

    @Override
    public void showCardsLoadingError() {
        Toaster.showLongToast(this, getResources().getString(R.string.cards_loading_error));
    }

    @Override
    public void unsetViewPager() {
        binding.cardsViewPager.setAdapter(null);
    }

    @Override
    public void showNoMoreLevelsDialog() {
        //TODO: improve
        Toaster.showShortToast(this, getResources().getString(R.string.no_more_levels_in_multiplayer));
    }

    private void setUpViews() {
        presenter.initAds();

        setUpViewPager();

        binding.nextCardButtonBackground.setOnClickListener(v -> presenter.onNextCardClicked());
        binding.backButtonBackground.setOnClickListener(v -> presenter.onBackClicked());
        binding.guideButtonBackground.setOnClickListener(v -> presenter.onGuideClicked());

        presenter.showCurrentLevel();
        presenter.showCoinBalance();
    }

    private void setUpViewPager() {
        CardsViewPagerAdapter cardsViewPagerAdapter = new CardsViewPagerAdapter(
                this,
                binding.cardsViewPager,
                (CardsViewPagerContract.Interactor) presenter.getInteractor()
        );
        cardsViewPagerAdapter.setUpWhenReady();
    }
}
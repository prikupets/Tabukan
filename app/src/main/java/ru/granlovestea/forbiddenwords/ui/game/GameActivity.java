package ru.granlovestea.forbiddenwords.ui.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import ru.granlovestea.forbiddenwords.databinding.ActivityGameBinding;
import ru.granlovestea.forbiddenwords.ui.game.deck.DeckViewPagerAdapter;
import ru.granlovestea.forbiddenwords.ui.game.deck.pagetransformer.HorizontalFlip;
import ru.granlovestea.forbiddenwords.ui.game.deck.pagetransformer.VerticalFlip;
import ru.granlovestea.forbiddenwords.ui.game.deck.pagetransformer.ZoomOut;

public class GameActivity extends AppCompatActivity implements GameContract.View {
    private GameContract.Presenter presenter;
    private ActivityGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if(presenter == null) {
            presenter = new GamePresenter();
        }

        setUpViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
        initAds();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detach();
    }

    @Override
    public void setOnNextCardClickedListener() {
        binding.nextCard.setOnClickListener((v) -> presenter.onNextCardClicked());
    }

    @Override
    public void showAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }

    @Override
    public void swipeNextCard() {
        binding.deckViewPager.setCurrentItem(binding.deckViewPager.getCurrentItem() + 1);
    }

    private void setUpViews() {
        setSupportActionBar(binding.toolbarWrapper.toolbar);

        binding.deckViewPager.setAdapter(new DeckViewPagerAdapter(this));
        binding.deckViewPager.setPageTransformer(new HorizontalFlip());

        setOnNextCardClickedListener();
    }

    private void initAds() {
        MobileAds.initialize(getApplicationContext(), initializationStatus -> { });
        presenter.initAds();
    }
}
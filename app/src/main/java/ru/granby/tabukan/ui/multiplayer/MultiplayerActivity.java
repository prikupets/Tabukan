package ru.granby.tabukan.ui.multiplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import ru.granby.tabukan.databinding.MultiplayerActivityBinding;
import ru.granby.tabukan.ui.multiplayer.deck.DeckViewPagerAdapter;
import ru.granby.tabukan.ui.multiplayer.deck.pagetransformer.HorizontalFlip;

public class MultiplayerActivity extends AppCompatActivity implements MultiplayerContract.View {
    private MultiplayerContract.Presenter presenter;
    private MultiplayerActivityBinding binding;

    @Override
    public void showAd() {

    }

    @Override
    public void swipeNextCard() {

    }

    @Override
    public void setOnNextCardClickedListener() {

    }

    @Override
    public void setOnBackClickedListener() {

    }

    @Override
    public void finishView() {

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = MultiplayerActivityBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(view);
//
//        if(presenter == null) {
//            presenter = new MultiplayerPresenter();
//        }
//
//        setUpViews();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        presenter.attach(this);
//        initAds();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        presenter.detach();
//    }
//
//    @Override
//    public void setOnNextCardClickedListener() {
//        binding.nextCard.setOnClickListener((v) -> presenter.onNextCardClicked());
//    }
//
//    @Override
//    public void showAd() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//        binding.adBanner.loadAd(adRequest);
//    }
//
//    @Override
//    public void swipeNextCard() {
//        binding.deckViewPager.setCurrentItem(binding.deckViewPager.getCurrentItem() + 1);
//    }
//
//    private void setUpViews() {
//        //setSupportActionBar(binding.toolbarWrapper.toolbar);
//
//        binding.deckViewPager.setAdapter(new DeckViewPagerAdapter(this));
//        binding.deckViewPager.setPageTransformer(new HorizontalFlip());
//
//        setOnNextCardClickedListener();
//        setOnBackClickedListener();
//    }
//
//    private void initAds() {
//        MobileAds.initialize(getApplicationContext(), initializationStatus -> { });
//        presenter.initAds();
//    }
//
//
//    @Override
//    public void setOnBackClickedListener() {
//        binding.backButton.setOnClickListener((v) -> presenter.onBackClicked());
//    }
//
//    @Override
//    public void finishView() {
//        finish();
//    }
}
package ru.granby.tabukan.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;

import ru.granby.tabukan.databinding.MenuActivityBinding;
import ru.granby.tabukan.model.business.interactor.MenuInteractor;
import ru.granby.tabukan.ui.multiplayer.MultiplayerActivity;
import ru.granby.tabukan.ui.singleplayer.SingleplayerActivity;


public class MenuActivity extends AppCompatActivity implements MenuContract.View {
    private static final String TAG = "~MenuActivity";
    protected MenuContract.Presenter presenter;
    private MenuActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MenuActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (presenter == null)
            presenter = new MenuPresenter();

        presenter.bind(this, new MenuInteractor());
        setUpViews();
    }

    @Override
    public void onResume(){
        super.onResume();

        if(!presenter.isViewBound()) {
            presenter.bind(this, new MenuInteractor());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbind();
    }

    @Override
    public void finishView() {

    }

    @Override
    public void showAdBanner(AdRequest adRequest) {
        binding.adBanner.loadAd(adRequest);
    }

    @Override
    public void setOnPlayMultiplayerClickedListener() {
        binding.playMultiplayerButtonBackground.setOnClickListener((v) -> presenter.onPlayMultiplayerClicked());
    }

    @Override
    public void startMultiplayerActivity() {
        startActivity(new Intent(this, MultiplayerActivity.class));
    }

    @Override
    public void setOnPlaySingleplayerClickedListener() {
        binding.playSingleplayerButtonBackground.setOnClickListener((v) -> presenter.onPlaySingleplayerClicked());
    }

    @Override
    public void startSingleplayerActivity() {
        startActivity(new Intent(this, SingleplayerActivity.class));
    }

    private void setUpViews() {
        presenter.initAds();
        setOnPlayMultiplayerClickedListener();
        setOnPlaySingleplayerClickedListener();
    }
}

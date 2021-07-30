package ru.granby.tabukan.ui.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;

import ru.granby.tabukan.R;
import ru.granby.tabukan.databinding.MenuActivityBinding;
import ru.granby.tabukan.model.business.interactor.MenuInteractor;
import ru.granby.tabukan.ui.multiplayer.MultiplayerActivity;
import ru.granby.tabukan.ui.singleplayer.SingleplayerActivity;
import ru.granby.tabukan.utils.Toaster;


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
    public void startMultiplayerActivity() {
        startActivity(new Intent(this, MultiplayerActivity.class));
    }

    @Override
    public void startSingleplayerActivity() {
        startActivity(new Intent(this, SingleplayerActivity.class));
    }

    @Override
    public void showAdsAlreadyRemoved() {
        Toaster.showLongToast(this, getResources().getString(R.string.ads_already_removed));
    }

    @Override
    public void showBuyRemoveAdsDialog() {
        // TODO: make actual buy ads dialog using google play

        new AlertDialog.Builder(this)
                .setPositiveButton("Buy", (dialog, id) -> presenter.onRemoveAdsBought())
                .setNeutralButton("Fake error", (dialog, id) -> presenter.onRemoveAdsPaymentError())
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public void showRemoveAdsBought() {
        Toaster.showShortToast(this, getResources().getString(R.string.thanks_for_the_purchase));
    }

    @Override
    public void showRemoveAdsPaymentError() {
        Toaster.showLongToast(this, getResources().getString(R.string.payment_error));
    }

    @Override
    public void hideAds() {
        binding.menuRoot.removeView(binding.adBanner);
    }

    private void setUpViews() {
        presenter.initAds();
        binding.playMultiplayerButtonBackground.setOnClickListener(v -> presenter.onPlayMultiplayerClicked());
        binding.playSingleplayerButtonBackground.setOnClickListener(v -> presenter.onPlaySingleplayerClicked());
        binding.removeAdsButtonBackground.setOnClickListener(v -> presenter.onRemoveAdsClicked());
    }
}

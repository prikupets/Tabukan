package ru.granby.tabukan.ui.store;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.granby.tabukan.databinding.StoreActivityBinding;
import ru.granby.tabukan.model.business.interactor.StoreInteractor;


public class StoreActivity extends AppCompatActivity implements StoreContract.View {
    private static final String TAG = "~StoreActivity";
    protected StoreContract.Presenter presenter;
    private StoreActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StoreActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (presenter == null)
            presenter = new StorePresenter();

        presenter.bind(this, new StoreInteractor());
        setUpViews();
    }

    @Override
    public void onResume(){
        super.onResume();

        if(!presenter.isViewBound()) {
            presenter.bind(this, new StoreInteractor());
        }
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

    private void setUpViews() {
        presenter.showCoinBalance();
        binding.backButtonBackground.setOnClickListener(v -> presenter.onBackButtonClicked());
        binding.coinsForAdButtonBackground.setOnClickListener(v -> presenter.onCoinsForAdClicked());
        binding.coinsPackPouch.getRoot().setOnClickListener(v -> presenter.onCoinsPouchClicked());
        binding.coinsPackBag.getRoot().setOnClickListener(v -> presenter.onCoinsBagClicked());
        binding.coinsPackChest.getRoot().setOnClickListener(v -> presenter.onChestClicked());
        binding.coinsPackBigChest.getRoot().setOnClickListener(v -> presenter.onBigChestClicked());

        presenter.showRegionalPrices();
    }

    @Override
    public void showRegionalPrices(String coinsPouchPrice,
                                   String coinsBagPrice,
                                   String coinsChestPrice,
                                   String coinsBigChestPrice) {
        binding.coinsPackChest.coinsPriceText.setText(coinsPouchPrice);
        binding.coinsPackBag.coinsPriceText.setText(coinsBagPrice);
        binding.coinsPackChest.coinsPriceText.setText(coinsChestPrice);
        binding.coinsPackBigChest.coinsPriceText.setText(coinsBigChestPrice);
    }

    @Override
    public void showCoinBalance(int coinBalance) {
        binding.coinsButtonText.setText(String.valueOf(coinBalance));
    }
}

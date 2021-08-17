package ru.granby.tabukan.ui.singleplayer;

import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.List;

import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import ru.granby.tabukan.R;
import ru.granby.tabukan.databinding.SingleplayerActivityBinding;
import ru.granby.tabukan.localization.Localizer;
import ru.granby.tabukan.model.business.interactor.SingleplayerInteractor;
import ru.granby.tabukan.model.data.database.relations.game.Association;
import ru.granby.tabukan.utils.Toaster;

import static ru.granby.tabukan.ui.singleplayer.SingleplayerContract.BLANK_LETTER;


public class SingleplayerActivity extends AppCompatActivity implements SingleplayerContract.View {
    private static final String TAG = "~SingleplayerActivity";
    protected SingleplayerContract.Presenter presenter;
    private SingleplayerActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SingleplayerActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (presenter == null)
            presenter = new SingleplayerPresenter();

        presenter.bind(this, new SingleplayerInteractor());
        setUpViews();
    }

    @Override
    public void onResume(){
        super.onResume();

        if(!presenter.isViewBound()) {
            presenter.bind(this, new SingleplayerInteractor());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideAds();
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
        binding.singleplayerRoot.removeView(binding.adBanner);
        binding.adBanner.destroy();
    }

    @Override
    public void finishView() {
        super.finish();
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
    public void showAssociation(int nextAssociationIndexToShow) {
        findViewById(binding.associations.getReferencedIds()[nextAssociationIndexToShow])
                .setVisibility(View.VISIBLE);
    }

    @Override
    public void showAssociationsUpTo(int lastAssociationIndex) {
        for (int i = 0; i <= lastAssociationIndex; i++) {
            showAssociation(i);
        }
    }

    @Override
    public void showAllAssociations() {
        showAssociationsUpTo(binding.associations.getReferencedIds().length - 1);
    }

    @Override
    public void showBlankWordLetters() {
        for (int wordLetterTextViewId : binding.wordLettersFlow.getReferencedIds()) {
            TextView wordLetterTextView = findViewById(wordLetterTextViewId);
            wordLetterTextView.setText(BLANK_LETTER.toString());
        }
    }

    @Override
    public void showSelectLetters(List<Character> selectLetters) {
        int[] selectLettersTextViewsIds = binding.selectLettersFlow.getReferencedIds();

        for (int i = 0; i < selectLettersTextViewsIds.length; i++) {
            TextView selectLetterTextView = findViewById(selectLettersTextViewsIds[i]);

            if(i < selectLetters.size() && !selectLetters.get(i).equals(BLANK_LETTER)) {
                selectLetterTextView.setText(selectLetters.get(i).toString());
                selectLetterTextView.setVisibility(View.VISIBLE);
            } else {
                selectLetterTextView.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void setUpAssociations(List<Association> associations) {
        for (int i = 0; i < associations.size(); i++) {
            TextView associationTextView = findViewById(binding.associations.getReferencedIds()[i]);
            associationTextView.setText(Localizer.getInstance().localize(associations.get(i).getLocalizedText()));
            associationTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showGotCoinsForPassingLevel(int coinCount) {
        //TODO
    }

    @Override
    public void showWordIsCorrect() {
        setGameUiClickable(false);
        binding.selectLettersFlow.setVisibility(View.INVISIBLE);

        binding.victoryConfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(500L)
                .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                .addSizes(new Size(12, 6f))
                .setPosition(binding.victoryConfetti.getWidth() / 2f,
                        binding.victoryConfetti.getHeight() / 2f)
                .burst(100);

        showWordCorrectnessAnimations(
                (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.singleplayer_correct_word_animation_scaling),
                R.drawable.singleplayer_word_letter_neutral_to_correct,
                presenter::showNextCardWithReward);
    }

    @Override
    public void showWordIsIncorrect() {
        showWordCorrectnessAnimations(
                (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.singleplayer_incorrect_word_animation_rotate),
                R.drawable.singleplayer_word_letter_neutral_to_incorrect,
                () -> {});
    }

    @Override
    public void showNotEnoughBalance() {
        //TODO: improve
        Toaster.showShortToast(this, getResources().getString(R.string.not_enough_balance));
    }

    @Override
    public void showNoMoreLevelsDialog() {
        //TODO: improve
        Toaster.showShortToast(this, getResources().getString(R.string.no_more_levels_in_singleplayer));
    }

    @Override
    public void showWordLetters(List<Character> wordLetters) {
        int[] wordLettersReferencedIds = binding.wordLettersFlow.getReferencedIds();

        for (int i = 0; i < wordLettersReferencedIds.length; i++) {
            TextView currentWordLetterTextView = (TextView) binding.getRoot().getViewById(wordLettersReferencedIds[i]);

            if (i < wordLetters.size()) {
                currentWordLetterTextView.setText(wordLetters.get(i).toString());
                currentWordLetterTextView.setVisibility(View.VISIBLE);
            } else {
                currentWordLetterTextView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void hideTooltips() {
        binding.tooltipAssociations.setVisibility(View.INVISIBLE);
        binding.tooltipHints.setVisibility(View.INVISIBLE);
        binding.tooltipSelectLetters.setVisibility(View.INVISIBLE);
        binding.tooltipWordLetters.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showTooltips() {
        binding.tooltipAssociations.setVisibility(View.VISIBLE);
        binding.tooltipHints.setVisibility(View.VISIBLE);
        binding.tooltipSelectLetters.setVisibility(View.VISIBLE);
        binding.tooltipWordLetters.setVisibility(View.VISIBLE);
    }

    @Override
    public void setGameUiClickable(boolean clickable) {
        for (int wordLetterId : binding.wordLettersFlow.getReferencedIds()) {
            findViewById(wordLetterId).setClickable(clickable);
        }

        binding.removeNeedlessSelectLettersButtonBackground.setClickable(clickable);
        binding.nextAssociationButtonBackground.setClickable(clickable);
        binding.skipCardButtonBackground.setClickable(clickable);
    }

    @Override
    public void setRemoveNeedlessSelectLettersClickable(boolean clickable) {
        binding.removeNeedlessSelectLettersButtonBackground.setClickable(clickable);
    }

    private void setUpViews() {
        presenter.initAds();

        binding.backButtonBackground.setOnClickListener(v -> presenter.onBackClicked());
        binding.removeNeedlessSelectLettersButtonBackground.setOnClickListener(v -> presenter.onRemoveNeedlessSelectLettersClicked());
        binding.nextAssociationButtonBackground.setOnClickListener(v -> presenter.onNextAssociationClicked());
        binding.skipCardButtonBackground.setOnClickListener(v -> presenter.onSkipCardClicked());
        binding.guideButtonBackground.setOnClickListener(v -> presenter.onGuideClicked());
        setWordLettersOnClickListeners();
        setSelectLettersOnClickListeners();

        presenter.showCoinBalance();
        presenter.showCurrentCard();
    }

    private void setWordLettersOnClickListeners() {
        int[] wordLettersReferencedIds = binding.wordLettersFlow.getReferencedIds();
        for (int i = 0; i < wordLettersReferencedIds.length; i++) {
            int finalIndex = i;

            findViewById(wordLettersReferencedIds[i]).setOnClickListener((v) ->
                    presenter.onWordLetterClicked(finalIndex, String.valueOf(((TextView) v).getText())));
        }
    }

    private void setSelectLettersOnClickListeners() {
        int[] selectLettersReferencedIds = binding.selectLettersFlow.getReferencedIds();
        for (int i = 0; i < selectLettersReferencedIds.length; i++) {
            int finalIndex = i;

            findViewById(selectLettersReferencedIds[i]).setOnClickListener((v) ->
                    presenter.onSelectLetterClicked(finalIndex, String.valueOf(((TextView) v).getText())));
        }
    }

    private void showWordCorrectnessAnimations(AnimationSet animationSet, int additionalAnimationDrawableId, Runnable onAnimationEnd) {
        Animation lastAnimation = animationSet.getAnimations()
                .get(animationSet.getAnimations().size() - 1);
        int totalDuration = (int) (lastAnimation.getStartOffset() + lastAnimation.getDuration());

        new Handler().postDelayed(
                onAnimationEnd,
                totalDuration * 2 + SingleplayerContract.DELAY_BEFORE_NEW_LEVEL);

        int[] wordLettersIds = binding.wordLettersFlow.getReferencedIds();
        for (int wordLetterId : wordLettersIds) {
            View wordLetter = findViewById(wordLetterId);
            if (wordLetter.getVisibility() == View.VISIBLE) {
                wordLetter.setBackground(AppCompatResources.getDrawable(this, additionalAnimationDrawableId));
                TransitionDrawable transition = (TransitionDrawable) wordLetter.getBackground();

                new Handler().postDelayed(
                        () -> transition.reverseTransition(totalDuration),
                        totalDuration);

                wordLetter.startAnimation(animationSet);
                transition.startTransition(totalDuration);
            }
        }
    }
}

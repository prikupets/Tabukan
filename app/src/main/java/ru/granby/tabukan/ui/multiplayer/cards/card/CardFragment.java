package ru.granby.tabukan.ui.multiplayer.cards.card;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.granby.tabukan.R;
import ru.granby.tabukan.databinding.MultiplayerCardFragmentBinding;
import ru.granby.tabukan.localization.Localizer;
import ru.granby.tabukan.model.business.interactor.CardInteractor;
import ru.granby.tabukan.model.data.database.relations.game.Association;
import ru.granby.tabukan.model.data.database.relations.game.Card;

public class CardFragment extends Fragment implements CardContract.View {
    private static final String TAG = "~CardFragment";
    private MultiplayerCardFragmentBinding binding;
    private CardContract.Presenter presenter;

    public static CardFragment newInstance(int cardIndex) {
        CardFragment cardFragment = new CardFragment();

        Bundle args = new Bundle();
        args.putInt("cardIndex", cardIndex);
        cardFragment.setArguments(args);

        return cardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MultiplayerCardFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (null == getArguments()) {
            Log.e(TAG, "onCreateView: getArguments() is null");
            throw new IllegalStateException();
        }

        presenter = new CardPresenter();
        presenter.bind(this, new CardInteractor());
        presenter.setCardIndex(getArguments().getInt("cardIndex", 0));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.showCard();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbind();
        binding = null;
    }

    @Override
    public void showCardTextData(Card card) {
        String headerText = Localizer.getInstance().localize(card.getHeader().getLocalizedText());

        binding.header.setText(headerText);
        for (Association tabu : card.getAssociations()) {
            if(binding.tabuWords.getChildCount() > CardContract.MAX_TABU_WORDS_COUNT) {
                break;
            }

            LayoutInflater inflater = LayoutInflater.from(getContext());
            TextView tabuWordTextView = (TextView) inflater.inflate(R.layout.multiplayer_tabu_word, null, false);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 0,1.0f);

            String tabuText = Localizer.getInstance().localize(tabu.getLocalizedText());
            tabuWordTextView.setText(tabuText);

            binding.tabuWords.addView(tabuWordTextView, layoutParams);
        }

        if(isCardImageTooSmall()) {
            binding.imageSwitcher.setVisibility(View.GONE);
        }

        showContent();
    }

    @Override
    public void showCardImage(Bitmap image) {
        binding.image.setImageBitmap(image);

        Animation inAnimation = new AlphaAnimation(0, 1);
        Animation outAnimation = new AlphaAnimation(1, 0);
        inAnimation.setDuration(CardContract.CHANGE_CARD_IMAGE_ANIMATION_DURATION);
        outAnimation.setDuration(CardContract.CHANGE_CARD_IMAGE_ANIMATION_DURATION);
        binding.imageSwitcher.setInAnimation(inAnimation);
        binding.imageSwitcher.setOutAnimation(outAnimation);
        binding.imageSwitcher.showNext();
    }

    @Override
    public void showTooltips() {
        binding.tooltipHeader.setVisibility(View.VISIBLE);
        binding.tooltipTabuWords.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTooltips() {
        binding.tooltipHeader.setVisibility(View.GONE);
        binding.tooltipTabuWords.setVisibility(View.GONE);
    }

    private boolean isCardImageTooSmall() {
        return binding.image.getHeight() < CardContract.CARD_IMAGE_MIN_HEIGHT;
    }

    private void showContent() {
        binding.content.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
    }
}

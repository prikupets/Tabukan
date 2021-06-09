package ru.granlovestea.forbiddenwords.ui.game.deck.card;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.granlovestea.forbiddenwords.R;
import ru.granlovestea.forbiddenwords.databinding.FragmentCardBinding;
import ru.granlovestea.forbiddenwords.model.domain.Card;
import ru.granlovestea.forbiddenwords.model.domain.Deck;
import ru.granlovestea.forbiddenwords.model.domain.ForbiddenWord;

public class CardFragment extends Fragment implements CardContract.View {
    private static final String TAG = "CARD_FRAGMENT";
    private CardContract.Presenter presenter;
    private FragmentCardBinding binding;

    public CardFragment() {

    }

    public static CardFragment newInstance(int position, Deck deck) {
        CardFragment cardFragment = new CardFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putSerializable("deck", deck);
        cardFragment.setArguments(args);

        return cardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        presenter = new CardPresenter();
        presenter.attach(this);

        if (null == getArguments()) {
            Log.e(TAG, "onCreateView: getArguments() is null");
            throw new IllegalStateException();
        }

        presenter.setDeck((Deck) getArguments().getSerializable("deck"));
        presenter.setPosition(getArguments().getInt("position", 0));
        presenter.setUpCard();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        presenter.detach();
    }

    @Override
    public void setUpCard(Card card) {
        binding.title.setText(card.getTitle());
        for (ForbiddenWord forbiddenWord : card.getForbiddenWords()) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            TextView forbiddenWordView = (TextView) inflater.inflate(R.layout.forbidden_word, null, false);

            forbiddenWordView.setText(forbiddenWord.getText());

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, 0,1.0f);
            binding.forbiddenWords.addView(forbiddenWordView, layoutParams);
        }
    }
}

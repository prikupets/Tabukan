package ru.granby.tabukan.ui.multiplayer.deck.card;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.granby.tabukan.databinding.MulitplayerFragmentCardBinding;
import ru.granby.tabukan.model.data.database.entity.game.DbCard;
import ru.granby.tabukan.model.data.database.entity.game.DbDeck;

public class CardFragment extends Fragment implements CardContract.View {
    private static final String TAG = "~CardFragment";
    private CardContract.Presenter presenter;
    private MulitplayerFragmentCardBinding binding;

    public CardFragment() {

    }

    public static CardFragment newInstance(int position, DbDeck deck) {
        CardFragment cardFragment = new CardFragment();

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putParcelable("deck", deck);
        cardFragment.setArguments(args);

        return cardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MulitplayerFragmentCardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        presenter = new CardPresenter();
        presenter.attach(this);

        if (null == getArguments()) {
            Log.e(TAG, "onCreateView: getArguments() is null");
            throw new IllegalStateException();
        }

        presenter.setDeck(getArguments().getParcelable("deck"));
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
    public void setUpCard(DbCard dbCard) {
        // TODO: FIX
//        binding.title.setText(card.getTitle());
//        for (Tabu tabu : card.getForbiddenWords()) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            TextView forbiddenWordView = (TextView) inflater.inflate(R.layout.forbidden_word, null, false);
//
//            forbiddenWordView.setText(tabu.getWord());
//
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    ActionBar.LayoutParams.MATCH_PARENT, 0,1.0f);
//            binding.forbiddenWords.addView(forbiddenWordView, layoutParams);
//        }
//
//        if(ifDisplayTooSmall()) {
//            binding.cardImage.setVisibility(View.GONE);
//        }
    }

    private boolean ifDisplayTooSmall() {
        return Resources.getSystem().getDisplayMetrics().heightPixels < 900;
    }
}

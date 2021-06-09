package ru.granlovestea.forbiddenwords.ui.game.deck;

import androidx.fragment.app.Fragment;

public class DeckViewPagerContract {
    interface View {
    }

    interface Presenter {
        void attach(View view);
        void detach();
        Fragment getFragment(int position);
        void setCurrentDeckIndex(int currentDeckIndex);
        int getItemsCount();
    }
}

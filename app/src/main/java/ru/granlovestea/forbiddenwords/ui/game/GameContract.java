package ru.granlovestea.forbiddenwords.ui.game;

import android.content.Context;
import android.view.View;

public class GameContract {
    interface View {
        void setOnNextCardClickedListener();
        void swipeNextCard();
        void showAd();
    }

    interface Presenter {
        void attach(View view);
        void detach();
        void onNextCardClicked();
        void initAds();
    }
}

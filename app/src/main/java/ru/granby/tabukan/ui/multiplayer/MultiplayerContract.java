package ru.granby.tabukan.ui.multiplayer;

public class MultiplayerContract {
    interface View {
        void showAd();
        void swipeNextCard();
        void setOnNextCardClickedListener();
        void setOnBackClickedListener();
        void finishView();
    }

    interface Presenter {
        void attach(View view);
        void detach();
        void initAds();
        void onNextCardClicked();
        void onBackClicked();
    }
}

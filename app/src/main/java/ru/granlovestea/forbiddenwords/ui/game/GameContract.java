package ru.granlovestea.forbiddenwords.ui.game;

import android.view.View;

public class GameContract {
    interface View {
        void onShowSampleTextClicked(android.view.View view);
        void setExampleText(String text);
    }

    interface Presenter {
        void attach(View view) ;
        void detach();

        void onShowSampleTextClicked();
    }
}

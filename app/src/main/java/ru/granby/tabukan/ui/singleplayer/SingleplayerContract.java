package ru.granby.tabukan.ui.singleplayer;

import com.google.android.gms.ads.AdRequest;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.granby.tabukan.model.data.database.relations.game.Association;
import ru.granby.tabukan.model.data.database.relations.game.Card;
import ru.granby.tabukan.ui.base.BaseContract;

public interface SingleplayerContract {
    Character BLANK_LETTER = ' ';
    int SELECT_LETTERS_COUNT = 16;
    int SKIP_CARD_PRICE = 5;
    int CARD_INDEX_TO_HIDE_TOOLTIPS = 2;
    int ASSOCIATION_INDEX_TO_HIDE_TOOLTIPS = 2;
    int TEMP_TOOLTIPS_DURATION = 7000; // ms
    int DELAY_BEFORE_NEW_LEVEL = 500; // ms
    int LEVEL_REWARD = 50; // TODO: rethink

    interface View extends BaseContract.View {
        void finishView();
        void showAdBanner(AdRequest adRequest);
        void hideAds();
        void showCoinBalance(int coinBalance);
        void showCurrentLevel(int level);
        void showAssociation(int nextAssociationIndexToShow);
        void showAssociationsUpTo(int lastAssociationIndex);
        void showAllAssociations();
        void showBlankWordLetters();
        void showWordLetters(List<Character> wordLetters);
        void showSelectLetters(List<Character> selectLetters);
        void setUpAssociations(List<Association> associations);
        void showGotCoinsForPassingLevel(int coinCount);
        void showWordIsCorrect();
        void showWordIsIncorrect();
        void showNotEnoughBalance();
        void showNoMoreLevelsDialog();
        void hideTooltips();
        void showTooltips();
        void setGameUiClickable(boolean clickable);
    }

    interface Presenter extends BaseContract.Presenter<View, Interactor> {
        void initAds();
        void onBackClicked();
        void onRemoveNeedlessSelectLettersClicked();
        void onNextAssociationClicked();
        void onSkipCardClicked();
        void showCoinBalance();
        void showCurrentCard();
        void onWordLetterClicked(int viewIndex, String viewText);
        void onSelectLetterClicked(int viewIndex, String viewText);
        void onGuideClicked();
        void showNextCardWithReward();
    }

    interface Interactor extends BaseContract.Interactor {
        Single<AdRequest> getAdRequest();
        Single<Boolean> isAdsRemoved();
        Single<Boolean> isSingleplayerFirstLaunch();
        Completable setSingleplayerFirstLaunch(boolean state);
        Single<Integer> getCoinBalance();
        Completable setCoinBalance(int coinBalance);
        Single<Integer> getCardsCount();
        Completable setCurrentCardIndex(int currentCardIndex);
        Single<Integer> getCurrentCardIndex();
        Single<Card> getCardByIndex(int cardIndex);
        Single<Card> getCurrentCard();
        Completable setCurrentCard();
        Single<Integer> getLastAssociationIndex();
        Completable setLastAssociationIndex(int nextAssociationIndexToShow);
        Single<List<Character>> getCurrentWordLetters();
        Completable setCurrentWordLetters(List<Character> wordLetters);
        Single<List<Character>> getCurrentSelectLetters();
        Completable setCurrentSelectLetters(List<Character> selectLetters);
    }
}

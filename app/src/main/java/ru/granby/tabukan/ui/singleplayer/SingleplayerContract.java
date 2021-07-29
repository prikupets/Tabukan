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

    interface View extends BaseContract.View {
        void showAdBanner(AdRequest adRequest);
        void showCoinBalance(int coinBalance);
        void showCurrentLevel(int level);
        void showAssociation(int nextAssociationIndexToShow);
        void showAssociationsUpTo(int lastAssociationIndex);
        void showAllAssociations();
        void showBlankWordLetters();
        void showWordLetters(List<Character> wordLetters);
        void showSelectLetters(List<Character> selectLetters);
        void setUpAssociations(List<Association> associations);
        void showLevelPassedDialog(int maxCoins, int receivedCoins);
        void showWordIsIncorrect();
        void showNotEnoughBalance();
        void showNoMoreLevelsDialog();
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
    }

    interface Interactor extends BaseContract.Interactor {
        Single<AdRequest> getAdRequest();
        Single<Boolean> isSingleplayerFirstLaunch();
        Completable setSingleplayerFirstLaunch(boolean isFirstLaunch);
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

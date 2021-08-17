package ru.granby.tabukan.ui.singleplayer;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.granby.tabukan.exception.AdsDisabledException;
import ru.granby.tabukan.exception.AlreadyShownAllAssociationsException;
import ru.granby.tabukan.exception.IncorrectCardIndexException;
import ru.granby.tabukan.exception.NotEnoughBalanceException;
import ru.granby.tabukan.exception.WordLettersAreFullException;
import ru.granby.tabukan.localization.Localizer;
import ru.granby.tabukan.model.business.dto.game.CardAndLastAssociationIndexAndWordLettersAndSelectLetters;
import ru.granby.tabukan.model.business.dto.game.CardAndWordLettersAndSelectLetters;
import ru.granby.tabukan.model.business.dto.game.CardIndexAndCardsCountAfterInterstitialAd;
import ru.granby.tabukan.model.business.helpers.CardIndexHelper;
import ru.granby.tabukan.model.business.helpers.CoinBalanceHelper;
import ru.granby.tabukan.model.data.database.relations.game.Card;
import ru.granby.tabukan.ui.base.BasePresenter;

import static ru.granby.tabukan.ui.singleplayer.SingleplayerContract.*;
import static ru.granby.tabukan.ui.singleplayer.SingleplayerContract.BLANK_LETTER;

public class SingleplayerPresenter extends BasePresenter<View, Interactor> implements Presenter {
    private static final String TAG = "~SingleplayerPresenter";

    @Nullable
    private InterstitialAd interstitialAd;

    @Override
    public void bind(View view, Interactor interactor) {
        super.bind(view, interactor);

        interactor.addDisposable(
                interactor.isSingleplayerFirstLaunch()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(result -> {
                            if(result) showTempTooltips();
                            return result;
                        })
                        .observeOn(Schedulers.io())
                        .flatMapCompletable(result -> result ? interactor.setSingleplayerFirstLaunch(false) : Completable.complete())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {},
                                throwable -> Log.e(TAG, "can't isSingleplayerFirstLaunch: ", throwable)
                        )
        );
    }

    @Override
    public void initAds() {
        interactor.addDisposable(
                interactor.isAdsEnabled()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMapCompletable(adsEnabled -> {
                            if(adsEnabled) {
                                return Completable.complete();
                            } else {
                                view.hideAds();
                                throw new AdsDisabledException("Ads disabled - shouldn't show it");
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    initAdsConfiguration();
                                    view.showAdBanner();
                                },
                                throwable -> {
                                    if(!(throwable instanceof AdsDisabledException))
                                        Log.e(TAG, "can't initAds: ", throwable);
                                }
                        )
        );
    }

    @Override
    public void onBackClicked() {
        view.finishView();
    }

    @Override
    public void showCoinBalance() {
        interactor.addDisposable(
                interactor.getCoinBalance()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                view::showCoinBalance,
                                throwable -> Log.e(TAG, "can't showCoinBalance: ", throwable)
                        )
        );
    }

    @Override
    public void showCurrentCard() {
        interactor.addDisposable(
                interactor.getCurrentCardIndex()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::showCard,
                                throwable -> Log.e(TAG, "can't getCurrentCardIndex: ", throwable)
                        )
        );
    }

    @Override
    public void onWordLetterClicked(int wordLetterIndex, String wordLetterText) {
        if(wordLetterText.equals(BLANK_LETTER.toString())) {
            return;
        }

        interactor.addDisposable(
                Single.zip(
                        interactor.getCurrentCard(),
                        interactor.getCurrentWordLetters(),
                        interactor.getCurrentSelectLetters(),
                        CardAndWordLettersAndSelectLetters::new
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .map(dto -> {
                            dto.getWordLetters().set(wordLetterIndex, BLANK_LETTER);
                            dto.getSelectLetters().set(dto.getSelectLetters().indexOf(BLANK_LETTER),
                                    wordLetterText.charAt(0));
                            return dto;
                        })
                        .flatMap(dto ->
                                Completable.mergeArray(
                                        interactor.setCurrentWordLetters(dto.getWordLetters()),
                                        interactor.setCurrentSelectLetters(dto.getSelectLetters())
                                ).andThen(Single.just(dto))
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((dto) -> {
                            view.showWordLetters(dto.getWordLetters());
                            view.showSelectLetters(dto.getSelectLetters());
                        }, throwable -> Log.e(TAG, "can't handle onWordLetterClicked", throwable))
        );
    }

    @Override
    public void onSelectLetterClicked(int selectLetterIndex, String selectLetterText) {
        interactor.addDisposable(
                Single.zip(
                        interactor.getCurrentCard(),
                        interactor.getCurrentWordLetters(),
                        interactor.getCurrentSelectLetters(),
                        CardAndWordLettersAndSelectLetters::new)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .map(dto -> {
                            int nextWordLetterToInsertIndex = dto.getWordLetters().indexOf(BLANK_LETTER);
                            if(dto.getWordLetters().isEmpty()) {
                                throw new IllegalStateException("wordLetters are empty");
                            }
                            else if(nextWordLetterToInsertIndex == -1) {
                                throw new WordLettersAreFullException("Can't find BLANK_LETTER in wordLetters");
                            } else {
                                dto.getWordLetters().set(nextWordLetterToInsertIndex, selectLetterText.charAt(0));
                                dto.getSelectLetters().set(selectLetterIndex, BLANK_LETTER);
                                return dto;
                            }
                        })
                        .flatMap(dto ->
                                Completable.mergeArray(
                                        interactor.setCurrentWordLetters(dto.getWordLetters()),
                                        interactor.setCurrentSelectLetters(dto.getSelectLetters())
                                ).andThen(Single.just(dto))
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((dto) -> {
                                    view.showWordLetters(dto.getWordLetters());
                                    view.showSelectLetters(dto.getSelectLetters());

                                    if(dto.getWordLetters().contains(BLANK_LETTER)) {
                                        return;
                                    }

                                    if(areWordLettersCorrect(dto.getWordLetters(), dto.getCard())) {
                                        view.showAllAssociations();
                                        //TODO: replace coinCount with normal (calculated, constant) value
                                        view.showWordIsCorrect();
                                        view.showGotCoinsForPassingLevel(5);
                                    } else {
                                        view.showWordIsIncorrect();
                                    }
                                }, throwable -> {
                                    if (throwable instanceof WordLettersAreFullException)
                                        Log.d(TAG, "didn't finish onSelectLetterClicked: wordLetters are full");
                                    else Log.e(TAG, "can't handle onSelectLetterClicked", throwable);
                                }
                        )
        );
    }

    @Override
    public void onGuideClicked() {
        showTempTooltips();
    }

    @Override
    public void onRemoveNeedlessSelectLettersClicked() {
        interactor.addDisposable(
                interactor.getCurrentCard()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .flatMap(card -> {
                            //TODO: check coin balance

                            String cardHeader = Localizer.getInstance().localize(
                                    card.getHeader().getLocalizedText());

                            ArrayList<Character> selectLetters = new ArrayList<>();
                            for (int i = 0; i < cardHeader.length(); i++) {
                                selectLetters.add(cardHeader.charAt(i));
                            }
                            Collections.shuffle(selectLetters);

                            List<Character> wordLetters = new ArrayList<>();
                            createBlankWordLettersList(card, wordLetters);

                            return Completable.mergeArray(
                                    interactor.setCurrentSelectLetters(selectLetters),
                                    interactor.setCurrentWordLetters(wordLetters),
                                    interactor.setRemoveNeedlessSelectLettersUsed(true)
                            ).andThen(Single.just(selectLetters));
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(selectLetters -> {
                                    view.showBlankWordLetters();
                                    view.showSelectLetters(selectLetters);
                                    view.setGameUiClickable(true);
                                    view.setRemoveNeedlessSelectLettersClickable(false);
                                }, (throwable -> Log.e(TAG, "can't onRemoveNeedlessSelectLettersClicked", throwable))
                        )
        );
    }

    @Override
    public void onNextAssociationClicked() {
        interactor.addDisposable(
                Single.zip(
                        interactor.getCurrentCard(),
                        interactor.getLastAssociationIndex(),
                        (card, lastShownAssociationIndex) -> {
                            int nextAssociationIndexToShow = lastShownAssociationIndex + 1;
                            if(nextAssociationIndexToShow >= card.getAssociations().size()) {
                                throw new AlreadyShownAllAssociationsException("nextAssociationIndex >= associationsCount");
                            }
                            return nextAssociationIndexToShow;
                        }
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .flatMap(nextAssociationIndexToShow ->
                                interactor.setLastAssociationIndex(nextAssociationIndexToShow)
                                        .andThen(Single.just(nextAssociationIndexToShow))
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(nextAssociationIndexToShow -> {
                                    view.showAssociation(nextAssociationIndexToShow);
                                    if(nextAssociationIndexToShow + 1 > ASSOCIATION_INDEX_TO_HIDE_TOOLTIPS) view.hideTooltips();
                                },
                                throwable -> {
                                    if(throwable instanceof AlreadyShownAllAssociationsException)
                                        Log.d(TAG, "didn't finish onNextAssociationClicked: already shown all associations");
                                    else
                                        Log.e(TAG, "can't show next association", throwable);
                                })
        );
    }

    @Override
    public void showNextCardWithReward() {
        interactor.addDisposable(
                interactor.getCoinBalance()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .map(coinBalance -> CoinBalanceHelper.deposit(coinBalance, LEVEL_REWARD))
                        .flatMap(newCoinBalance ->
                                interactor.setCoinBalance(newCoinBalance)
                                        .andThen(Single.just(newCoinBalance)))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                newCoinBalance -> {
                                    view.showCoinBalance(newCoinBalance);
                                    showNextCard();
                                },
                                throwable -> {}
                        )
        );
    }

    @Override
    public void onSkipCardClicked() {
        interactor.addDisposable(
                interactor.getCoinBalance()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .map(coinBalance -> CoinBalanceHelper.withdraw(coinBalance, SKIP_CARD_PRICE))
                        .flatMap(newCoinBalance ->
                                interactor.setCoinBalance(newCoinBalance)
                                        .andThen(Single.just(newCoinBalance)))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                newCoinBalance -> {
                                    view.showCoinBalance(newCoinBalance);
                                    showNextCard();
                                },
                                throwable -> {
                                    if(throwable instanceof NotEnoughBalanceException) {
                                        Log.i(TAG, "onSkipCardClicked: not enough balance");
                                        view.showNotEnoughBalance();
                                    } else {
                                        Log.e(TAG, "can't skip card: ", throwable);
                                    }
                                }
                        )
        );
    }

    private void showNextCard() {
        interactor.addDisposable(
                Single.zip(
                        interactor.getCardsCount(),
                        interactor.getCurrentCardIndex(),
                        (cardsCount, cardIndex) -> CardIndexHelper.changeIndexTo(cardIndex + 1, cardsCount)
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .flatMap(cardIndex ->
                                Completable.mergeArray(
                                        interactor.setCurrentCardIndex(cardIndex),
                                        interactor.setLastAssociationIndex(0),
                                        interactor.setCurrentWordLetters(new ArrayList<>()),
                                        interactor.setCurrentSelectLetters(new ArrayList<>()),
                                        interactor.setRemoveNeedlessSelectLettersUsed(false)
                                ).andThen(Single.just(cardIndex))
                        )
                        .zipWith(
                                interactor.getCardsCountAfterInterstitialAd(),
                                CardIndexAndCardsCountAfterInterstitialAd::new)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                dto -> {
                                    doIfAdsEnabled(() -> {
                                        if(dto.getCardsCountAfterInterstitialAd() >= CARDS_COUNT_TO_INTERSTITIAL_AD) {
                                            tryShowInterstitialAd();
                                        } else {
                                            addCardsCountAfterInterstitialAds();
                                        }
                                    });

                                    showCard(dto.getCardIndex());
                                },
                                throwable -> {
                                    if(throwable instanceof IncorrectCardIndexException) {
                                        Log.i(TAG, "onSkipCardClicked: incorrect card index");
                                        view.showNoMoreLevelsDialog();
                                    } else {
                                        Log.e(TAG, "can't skip card: ", throwable);
                                    }
                                }
                        )
        );
    }

    private void showCard(int cardIndex) {
        doIfAdsEnabled(() -> {
            if(interstitialAd == null) {
                loadInterstitialAd(false);
            }
        });

        interactor.addDisposable(
                Single.zip(
                        interactor.getCardByIndex(cardIndex),
                        interactor.getLastAssociationIndex(),
                        interactor.getCurrentWordLetters(),
                        interactor.getCurrentSelectLetters(),
                        CardAndLastAssociationIndexAndWordLettersAndSelectLetters::new
                ).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(dto -> {
                    if(dto.getWordLetters().isEmpty()) {
                        createBlankWordLettersList(dto.getCard(), dto.getWordLetters());
                        return interactor.setCurrentWordLetters(dto.getWordLetters())
                                .andThen(Single.just(dto));
                    }

                    return Single.just(dto);
                })
                .flatMap(dto -> {
                    if(dto.getSelectLetters().isEmpty()) {
                        createSelectLettersList(dto.getCard(), dto.getSelectLetters());
                        return interactor.setCurrentSelectLetters(dto.getSelectLetters())
                                .andThen(Single.just(dto));
                    }

                    return Single.just(dto);
                })
                .flatMap(dto ->
                        interactor.setCurrentCard()
                                .andThen(Single.just(dto))
                )
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapCompletable(dto -> {
                    view.setUpAssociations(dto.getCard().getAssociations());
                    view.showAssociationsUpTo(dto.getLastAssociationIndex());
                    view.showCurrentLevel(cardIndex + 1);
                    view.showWordLetters(dto.getWordLetters());
                    view.showSelectLetters(dto.getSelectLetters());
                    view.setGameUiClickable(true);
                    return Completable.complete();
                })
                .observeOn(Schedulers.io())
                .andThen(interactor.isRemoveNeedlessSelectLettersUsed())
                .subscribe(isRemoveNeedlessSelectLettersUsed ->
                                view.setRemoveNeedlessSelectLettersClickable(!isRemoveNeedlessSelectLettersUsed),
                        throwable -> Log.e(TAG, "can't showCard: ", throwable)
                )
        );
    }

    private void createBlankWordLettersList(Card card, List<Character> wordLetters) {
        for (int i = 0; i < Localizer.getInstance().localize(card.getHeader().getLocalizedText()).length(); i++) {
            wordLetters.add(BLANK_LETTER);
        }
    }

    private void createSelectLettersList(Card card, List<Character> selectLetters) {
        String cardHeader = Localizer.getInstance().localize(card.getHeader().getLocalizedText());

        for (int i = 0; i < cardHeader.length(); i++) {
            selectLetters.add(cardHeader.charAt(i));
        }

        char[] alphabetLetters = Localizer.getInstance().getAlphabet();
        for (int i = 0; i < SELECT_LETTERS_COUNT - cardHeader.length(); i++) {
            selectLetters.add(alphabetLetters[new Random().nextInt(alphabetLetters.length)]);
        }

        Collections.shuffle(selectLetters);
    }

    private boolean areWordLettersCorrect(List<Character> wordLetters, Card card) {
        String cardHeader = Localizer.getInstance()
                .localize(card.getHeader().getLocalizedText())
                .toLowerCase();

        for (int i = 0; i < wordLetters.size(); i++) {
            if(!wordLetters.get(i).equals(cardHeader.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private void showTempTooltips() {
        view.showTooltips();
        interactor.addDisposable(Completable.complete()
                .delay(TEMP_TOOLTIPS_DURATION, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> view.hideTooltips()));
    }

    private void initAdsConfiguration() {
        RequestConfiguration configuration = new RequestConfiguration.Builder()
                .setTestDeviceIds(Collections.singletonList(AdRequest.DEVICE_ID_EMULATOR))
                .build();
        MobileAds.setRequestConfiguration(configuration);
    }

    private void loadInterstitialAd(boolean showOnLoad) {
        view.loadInterstitialAd(new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd loadedInterstitialAd) {
                Log.i(TAG, "loadInterstitialAd: onAdLoaded");
                interstitialAd = loadedInterstitialAd;
                if(showOnLoad) {
                    tryShowInterstitialAd();
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.w(TAG, "loadInterstitialAd: onAdFailedToLoad: " + loadAdError.getMessage());
                interstitialAd = null;
            }
        });
    }

    private void tryShowInterstitialAd() {
        if(interstitialAd == null) {
            loadInterstitialAd(true);
        } else {
            resetCardsCountAfterInterstitialAds();
            view.showInterstitialAd(interstitialAd);
            interstitialAd = null;
        }
    }

    private void addCardsCountAfterInterstitialAds() {
        interactor.addDisposable(
                interactor.getCardsCountAfterInterstitialAd()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .flatMapCompletable(cardsCount ->
                                interactor.setCardsCountAfterInterstitialAd(cardsCount + 1))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {},
                                throwable -> Log.e(TAG, "Can't addCardsCountAfterInterstitialAds", throwable))
        );
    }

    private void resetCardsCountAfterInterstitialAds() {
        interactor.addDisposable(
                interactor.setDefaultCardsCountAfterInterstitialAd()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {},
                                throwable -> Log.e(TAG, "Can't resetCardsCountAfterInterstitialAds", throwable))
        );
    }

    private void doIfAdsEnabled(Action action) {
        interactor.addDisposable(
                interactor.isAdsEnabled()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(adsEnabled -> {
                            if(adsEnabled) {
                                action.run();
                            }
                        }, throwable -> Log.e(TAG, "doIfAdsEnabled: ", throwable))
        );
    }
}
package ru.granby.tabukan.model.business.interactor.keys;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerKey {
    public static final String SUFFIX = "_MULTIPLAYER";

    public static final String FIRST_LAUNCH = "FIRST_LAUNCH" + SUFFIX;
    public static final boolean FIRST_LAUNCH_DEFAULT_VALUE = true;

    public static final String CARDS_COUNT = "CARDS_COUNT" + SUFFIX;

    public static final String CARD = "CARD" + SUFFIX;

    public static final String CARD_IMAGE = "CARD_IMAGE" + SUFFIX;

    public static final String CURRENT_CARD_INDEX = "CURRENT_CARD_INDEX" + SUFFIX;
    public static final int CURRENT_CARD_INDEX_DEFAULT_VALUE = 0;

    public static final String CURRENT_CARD = "CURRENT_CARD" + SUFFIX;

    public static final String CARDS_COUNT_AFTER_INTERSTITIAL_AD = "CARDS_AFTER_INTERSTITIAL_AD" + SUFFIX;
    public static final int CARDS_COUNT_AFTER_INTERSTITIAL_AD_DEFAULT_VALUE = 1;

    public static final String MAX_CARD_INDEX = "MAX_CARD_INDEX" + SUFFIX;
    public static final int MAX_CARD_INDEX_DEFAULT_VALUE = 0;

}

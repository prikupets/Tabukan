package ru.granby.tabukan.model.business.interactor.keys;

public class ApplicationKey {
    private static final String SUFFIX = "_APP";

    public static final String COIN_BALANCE = "COIN_BALANCE" + SUFFIX;
    public static final int COIN_BALANCE_DEFAULT_VALUE = 250;

    public static final String ADS_REMOVED = "ADS_REMOVED" + SUFFIX;
    public static final boolean ADS_REMOVED_DEFAULT = false;

}

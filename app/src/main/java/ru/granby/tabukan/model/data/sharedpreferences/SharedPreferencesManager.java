package ru.granby.tabukan.model.data.sharedpreferences;

import ru.granby.tabukan.App;

public class SharedPreferencesManager extends SharedPreferencesBase {
    private static final String SHARED_PREFERENCES_TAG = "tabukan";
    private static final String SHARED_PREFERENCES_KEY = "wc<F,[WRZG,8aY^Q";
    private static SharedPreferencesManager instance;

    protected SharedPreferencesManager(String sharedPreferencesTag, String sharedPreferencesPassword) {
        super(sharedPreferencesTag, sharedPreferencesPassword);
    }

    public static SharedPreferencesManager getInstance() {
        if(instance == null) {
            instance = new SharedPreferencesManager(SHARED_PREFERENCES_TAG, SHARED_PREFERENCES_KEY);
        }

        return instance;
    }
}

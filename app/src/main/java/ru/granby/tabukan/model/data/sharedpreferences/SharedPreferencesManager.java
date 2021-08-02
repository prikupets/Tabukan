package ru.granby.tabukan.model.data.sharedpreferences;

import ru.granby.tabukan.App;
import ru.granby.tabukan.R;

public class SharedPreferencesManager extends SecureRxSharedPreferences {
    private static final String SHARED_PREFERENCES_TAG = "tabukan";
    private static String sharedPreferencesKey;
    private static SharedPreferencesManager instance;

    protected SharedPreferencesManager(String sharedPreferencesTag, String sharedPreferencesPassword) {
        super(sharedPreferencesTag, sharedPreferencesPassword);
    }

    public static SharedPreferencesManager getInstance() {
        if(instance == null) {
            sharedPreferencesKey = App.getInstance().getString(R.string.shared_preferences_key);
            instance = new SharedPreferencesManager(SHARED_PREFERENCES_TAG, sharedPreferencesKey);
        }

        return instance;
    }
}

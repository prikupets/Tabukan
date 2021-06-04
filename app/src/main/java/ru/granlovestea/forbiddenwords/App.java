package ru.granlovestea.forbiddenwords;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import ru.granlovestea.forbiddenwords.model.DataManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.setup(getResources());
    }
}

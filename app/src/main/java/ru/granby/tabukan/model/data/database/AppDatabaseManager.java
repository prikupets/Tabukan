package ru.granby.tabukan.model.data.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.Arrays;

import ru.granby.tabukan.App;

public class AppDatabaseManager {
    private static final String TAG = "~AppDatabaseManager";
    private static final String DATABASE_NAME = "tabukan";
    private static AppDatabase database;

    public static AppDatabase getDatabase() {
        if(database == null) {
            Context context = App.getInstance();

            if (Arrays.asList(context.databaseList()).contains(DATABASE_NAME)) {
                Log.i(TAG, "getDb: db already exists");
                database = Room.databaseBuilder(context,
                        AppDatabase.class, DATABASE_NAME)
                        .build();
            } else {
                Log.i(TAG, "getDb: db doesn't exist, creating from assets");
                database = Room.databaseBuilder(context,
                        AppDatabase.class, DATABASE_NAME)
                        .createFromAsset("database/" + DATABASE_NAME + ".sqlite")
                        .build();
            }

            Log.i(TAG, "Set up DB");
        }

        return database;
    }
}

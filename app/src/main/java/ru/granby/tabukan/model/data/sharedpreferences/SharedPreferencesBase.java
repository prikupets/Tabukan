package ru.granby.tabukan.model.data.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;

import java.lang.reflect.Type;
import java.util.Set;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import ru.granby.tabukan.App;
import ru.granby.tabukan.BuildConfig;

abstract class SharedPreferencesBase {
    private static final String TAG = "~SharedPreferencesBase";
    private Gson gson;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesBase(String preferencesTag, String password) {
        if(BuildConfig.DEBUG) {
            sharedPreferences = App.getInstance().getSharedPreferences(preferencesTag, Context.MODE_PRIVATE);
        } else {
            sharedPreferences = new SecurePreferences(
                    App.getInstance(),
                    password,
                    preferencesTag);
        }
        gson = new Gson();
    }

    public @NonNull Single<Boolean> getBoolean(String key, boolean defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getBoolean(key, defaultValue));
    }

    public @NonNull Single<Float> getFloat(String key, float defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getFloat(key, defaultValue));
    }

    public @NonNull Single<Integer> getInt(String key, int defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getInt(key, defaultValue));
    }

    public @NonNull Single<Long> getLong(String key, long defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getLong(key, defaultValue));
    }

    public @NonNull Single<String> getString(String key, String defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getString(key, defaultValue));
    }

    public @NonNull Single<Set<String>> getStringSet(String key, Set<String> defaultValues) {
        return Single.fromCallable(() -> sharedPreferences.getStringSet(key, defaultValues));
    }

    public @NonNull <T> Single<T> getObject(String key, Object defaultValue, Class<T> objectClass) {
        return Single.fromCallable(() -> sharedPreferences.getString(key, defaultValue.toString()))
                .map((serializedObject) -> gson.fromJson(serializedObject, objectClass));
    }

    public @NonNull <T> Single<T> getObject(String key, Object defaultValue, Type type) {
        return Single.fromCallable(() -> sharedPreferences.getString(key, defaultValue.toString()))
                .map((serializedObject) -> gson.fromJson(serializedObject, type));
    }

    public Completable putBoolean(String key, boolean value) {
        return put((editor) -> editor.putBoolean(key, value));
    }

    public Completable putFloat(String key, float value) {
        return put((editor) -> editor.putFloat(key, value));
    }

    public Completable putInt(String key, int value) {
        return put((editor) -> editor.putInt(key, value));
    }

    public Completable putLong(String key, long value) {
        return put((editor) -> editor.putLong(key, value));
    }

    public Completable putString(String key, String value) {
        return put((editor) -> editor.putString(key, value));
    }

    public Completable putStringSet(String key, Set<String> values) {
        return put((editor) -> editor.putStringSet(key, values));
    }

    public Completable putObject(String key, Object value) {
        return put((editor) -> editor.putString(key, gson.toJson(value)));
    }

    private Completable put(Consumer<Editor> putConsumer) {
        return Completable.fromAction(() -> {
            Editor editor = sharedPreferences.edit();
            putConsumer.accept(editor);
            editor.apply();
        });
    }
}

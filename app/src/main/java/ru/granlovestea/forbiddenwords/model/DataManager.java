package ru.granlovestea.forbiddenwords.model;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import ru.granlovestea.forbiddenwords.model.domain.Deck;


public class DataManager {
    private static Gson gson;
    private static Resources resources;

    public static void setup(Resources resources) {
        gson = new GsonBuilder().create();
        DataManager.resources = resources;
    }

    public static <T> T fromJSON(JsonArray jsonArray, Class<T> classOfT) {
        return gson.fromJson(jsonArray, classOfT);
    }

    public static <T> T fromJSON(JsonArray jsonArray, Type type) {
        return gson.fromJson(jsonArray, type);
    }

    public static <T> T fromJSON(int id, Class<T> classOfT) {
        return gson.fromJson(getResourceInputStreamReader(id), classOfT);
    }

    public static <T> T fromJSON(int id, Type type) {
        return gson.fromJson(getResourceInputStreamReader(id), type);
    }

    public static Deck fromJSON(JsonElement jsonElement, Type type) {
        return gson.fromJson(jsonElement, type);
    }

    private static InputStreamReader getResourceInputStreamReader(int id) {
        return new InputStreamReader(resources.openRawResource(id));
    }
}

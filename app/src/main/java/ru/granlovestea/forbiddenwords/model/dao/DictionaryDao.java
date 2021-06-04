package ru.granlovestea.forbiddenwords.model.dao;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ru.granlovestea.forbiddenwords.R;
import ru.granlovestea.forbiddenwords.model.DataManager;
import ru.granlovestea.forbiddenwords.model.domain.Dictionary;

public class DictionaryDao {
    public List<Dictionary> getAll() {
        Type dictionaryListType = new TypeToken<List<Dictionary>>(){}.getType();

        return DataManager.fromJSON(
                DataManager.fromJSON(R.raw.content, JsonObject.class)
                        .getAsJsonArray("dictionaries"),
                dictionaryListType);
    }
}

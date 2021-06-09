package ru.granlovestea.forbiddenwords.model.dao;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ru.granlovestea.forbiddenwords.R;
import ru.granlovestea.forbiddenwords.model.DataManager;
import ru.granlovestea.forbiddenwords.model.domain.Deck;

public class DeckDao {

    // TODO: replace JSON with DB.
    public Deck getDeckByIndex(int deckIndex) {
        Type deckType = new TypeToken<Deck>(){}.getType();

        return DataManager.fromJSON(
                DataManager.fromJSON(R.raw.content, JsonObject.class)
                        .getAsJsonArray("decks")
                        .get(deckIndex),
                deckType);
    }
}

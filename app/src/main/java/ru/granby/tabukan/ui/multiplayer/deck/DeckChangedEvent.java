package ru.granby.tabukan.ui.multiplayer.deck;

import ru.granby.tabukan.model.data.database.entity.game.DbDeck;

public class DeckChangedEvent {
    public DbDeck deck;

    public DeckChangedEvent(DbDeck deck) {
        this.deck = deck;
    }
}

package ru.granlovestea.forbiddenwords.ui.game.deck;

import ru.granlovestea.forbiddenwords.model.domain.Deck;

public class DeckChangedEvent {
    public Deck deck;

    public DeckChangedEvent(Deck deck) {
        this.deck = deck;
    }
}

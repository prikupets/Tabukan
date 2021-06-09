package ru.granlovestea.forbiddenwords.model.domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Deck implements Serializable {
    private String name;
    private List<Card> cards;

    public Deck(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck that = (Deck) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(cards, that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cards);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}

package ru.granlovestea.forbiddenwords.model.domain;

import java.util.List;
import java.util.Objects;

public class Card {
    private String word;
    private List<String> forbiddenWords;

    public Card(String word, List<String> forbiddenWords) {
        this.word = word;
        this.forbiddenWords = forbiddenWords;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getForbiddenWords() {
        return forbiddenWords;
    }

    public void setForbiddenWords(List<String> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(word, card.word) &&
                Objects.equals(forbiddenWords, card.forbiddenWords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, forbiddenWords);
    }
}
package ru.granlovestea.forbiddenwords.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Card implements Serializable {
    private String title;
    private String imageUrl;
    private List<ForbiddenWord> forbiddenWords;

    public Card(String title, String imageUrl, List<ForbiddenWord> forbiddenWords) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.forbiddenWords = forbiddenWords;
    }

    public Card(String title, List<ForbiddenWord> forbiddenWords) {
        this.title = title;
        this.forbiddenWords = forbiddenWords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ForbiddenWord> getForbiddenWords() {
        return forbiddenWords;
    }

    public void setForbiddenWords(List<ForbiddenWord> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(title, card.title) &&
                Objects.equals(imageUrl, card.imageUrl) &&
                Objects.equals(forbiddenWords, card.forbiddenWords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, imageUrl, forbiddenWords);
    }
}
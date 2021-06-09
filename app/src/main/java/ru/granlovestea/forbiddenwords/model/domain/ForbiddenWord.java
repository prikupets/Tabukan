package ru.granlovestea.forbiddenwords.model.domain;

public class ForbiddenWord {
    private String text;
    private boolean isPermitted = false;

    public ForbiddenWord(String text, boolean isPermitted) {
        this.text = text;
        this.isPermitted = isPermitted;
    }

    public ForbiddenWord(String text) {
        this(text, false);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPermitted() {
        return isPermitted;
    }

    public void setPermitted(boolean permitted) {
        isPermitted = permitted;
    }
}

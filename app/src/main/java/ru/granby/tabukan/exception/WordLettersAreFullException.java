package ru.granby.tabukan.exception;

public class WordLettersAreFullException extends BusinessException {
    public WordLettersAreFullException(String message) {
        super(message);
    }

    public WordLettersAreFullException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordLettersAreFullException(Throwable cause) {
        super(cause);
    }

    public WordLettersAreFullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

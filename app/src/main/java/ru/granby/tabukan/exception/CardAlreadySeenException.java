package ru.granby.tabukan.exception;

public class CardAlreadySeenException extends BusinessException {
    public CardAlreadySeenException(String message) {
        super(message);
    }

    public CardAlreadySeenException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardAlreadySeenException(Throwable cause) {
        super(cause);
    }

    public CardAlreadySeenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

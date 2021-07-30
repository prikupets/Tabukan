package ru.granby.tabukan.exception;

public class AdsRemovedException extends BusinessException {
    public AdsRemovedException(String message) {
        super(message);
    }

    public AdsRemovedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdsRemovedException(Throwable cause) {
        super(cause);
    }

    public AdsRemovedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package ru.granby.tabukan.exception;

public class AdsAlreadyRemovedException extends BusinessException {
    public AdsAlreadyRemovedException(String message) {
        super(message);
    }

    public AdsAlreadyRemovedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdsAlreadyRemovedException(Throwable cause) {
        super(cause);
    }

    public AdsAlreadyRemovedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

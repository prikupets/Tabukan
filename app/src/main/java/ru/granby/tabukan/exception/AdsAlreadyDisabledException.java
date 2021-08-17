package ru.granby.tabukan.exception;

public class AdsAlreadyDisabledException extends BusinessException {
    public AdsAlreadyDisabledException(String message) {
        super(message);
    }

    public AdsAlreadyDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdsAlreadyDisabledException(Throwable cause) {
        super(cause);
    }

    public AdsAlreadyDisabledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

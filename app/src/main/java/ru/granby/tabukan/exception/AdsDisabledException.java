package ru.granby.tabukan.exception;

public class AdsDisabledException extends BusinessException {
    public AdsDisabledException(String message) {
        super(message);
    }

    public AdsDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdsDisabledException(Throwable cause) {
        super(cause);
    }

    public AdsDisabledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

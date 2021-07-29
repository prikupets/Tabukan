package ru.granby.tabukan.exception;

public class NotEnoughCoinsException extends BusinessException {
    public NotEnoughCoinsException(String message) {
        super(message);
    }

    public NotEnoughCoinsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughCoinsException(Throwable cause) {
        super(cause);
    }

    public NotEnoughCoinsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

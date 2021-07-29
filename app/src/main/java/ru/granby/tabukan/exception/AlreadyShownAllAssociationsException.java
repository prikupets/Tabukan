package ru.granby.tabukan.exception;

public class AlreadyShownAllAssociationsException extends BusinessException {
    public AlreadyShownAllAssociationsException(String message) {
        super(message);
    }

    public AlreadyShownAllAssociationsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyShownAllAssociationsException(Throwable cause) {
        super(cause);
    }

    public AlreadyShownAllAssociationsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

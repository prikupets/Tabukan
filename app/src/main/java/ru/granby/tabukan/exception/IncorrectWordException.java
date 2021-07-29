package ru.granby.tabukan.exception;

public class IncorrectWordException extends BusinessException {
    public IncorrectWordException(String message) {
        super(message);
    }

    public IncorrectWordException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectWordException(Throwable cause) {
        super(cause);
    }

    public IncorrectWordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

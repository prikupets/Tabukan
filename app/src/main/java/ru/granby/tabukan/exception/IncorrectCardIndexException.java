package ru.granby.tabukan.exception;

public class IncorrectCardIndexException extends BusinessException {
    public IncorrectCardIndexException(String message) {
        super(message);
    }

    public IncorrectCardIndexException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCardIndexException(Throwable cause) {
        super(cause);
    }

    public IncorrectCardIndexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

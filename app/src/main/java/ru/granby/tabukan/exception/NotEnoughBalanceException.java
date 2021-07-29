package ru.granby.tabukan.exception;

public class NotEnoughBalanceException extends BusinessException {
    public NotEnoughBalanceException(String message) {
        super(message);
    }

    public NotEnoughBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughBalanceException(Throwable cause) {
        super(cause);
    }

    public NotEnoughBalanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

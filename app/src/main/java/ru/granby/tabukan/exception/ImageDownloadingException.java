package ru.granby.tabukan.exception;

import java.io.IOException;

public class ImageDownloadingException extends IOException {
    public ImageDownloadingException() {
        super();
    }

    public ImageDownloadingException(String message) {
        super(message);
    }

    public ImageDownloadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageDownloadingException(Throwable cause) {
        super(cause);
    }
}

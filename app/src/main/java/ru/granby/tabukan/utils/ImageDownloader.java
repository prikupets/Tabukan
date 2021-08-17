package ru.granby.tabukan.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;


import java.io.IOException;
import java.net.URL;

import io.reactivex.rxjava3.core.Single;
import ru.granby.tabukan.exception.ImageDownloadingException;

public class ImageDownloader {
    private static final String TAG = "~ImageDownloader";

    private ImageDownloader() {
        throw new IllegalStateException("Utility class");
    }

    public static Single<Bitmap> download(String urlText) {
        return Single.fromCallable(() -> {
                    try {
                        Bitmap image = BitmapFactory.decodeStream(new URL(urlText).openConnection().getInputStream());
                        Log.i(TAG, String.format("Image size=%s width=%s height=%s",
                                image.getByteCount(), image.getWidth(), image.getHeight()));
                        return image;
                    } catch (IOException e) {
                        throw new ImageDownloadingException();
                    }
                }
        );
    }
}

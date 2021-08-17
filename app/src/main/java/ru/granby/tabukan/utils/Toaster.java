package ru.granby.tabukan.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import ru.granby.tabukan.BuildConfig;
import ru.granby.tabukan.R;

public class Toaster {
    private Toaster() {
        throw new IllegalStateException("Utility class");
    }

    public static void showShortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}

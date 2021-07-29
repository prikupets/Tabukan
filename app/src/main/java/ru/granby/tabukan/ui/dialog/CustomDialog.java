package ru.granby.tabukan.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.viewbinding.ViewBinding;

public abstract class CustomDialog {
    protected Dialog dialog;

    public CustomDialog(Context context, View contentView) {
        dialog = new Dialog(context);
        dialog.setContentView(contentView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}

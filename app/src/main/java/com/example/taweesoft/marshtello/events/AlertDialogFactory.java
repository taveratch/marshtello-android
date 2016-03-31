package com.example.taweesoft.marshtello.events;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by TAWEESOFT on 3/14/16 AD.
 */
public class AlertDialogFactory {
    private AlertDialogFactory(){}

    public static AlertDialog.Builder newInstance(Context context, String title, String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        return builder;
    }

    public static AlertDialog.Builder newInstance(Context context , String title, String content, AlertDialogButtonAction positive, AlertDialogButtonAction cancel , AlertDialogButtonAction neutral){
        AlertDialog.Builder dialog = newInstance(context, title,content);
        dialog.setPositiveButton(positive.getButtonText(), positive);
        dialog.setNegativeButton(cancel.getButtonText(), cancel);
        dialog.setNeutralButton(neutral.getButtonText() , neutral);
        return dialog;
    }
}

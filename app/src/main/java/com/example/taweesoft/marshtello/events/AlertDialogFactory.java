package com.example.taweesoft.marshtello.events;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * AlertDialog Builder factory.
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

    public static AlertDialog.Builder newInstance(Context context , String title, String content, AlertDialogButtonAction positive, AlertDialogButtonAction negative , AlertDialogButtonAction neutral){
        AlertDialog.Builder dialog = newInstance(context, title,content,positive,negative);
        dialog.setNeutralButton(neutral.getButtonText(), neutral);
        return dialog;
    }

    public static AlertDialog.Builder newInstance(Context context , String title , String content , AlertDialogButtonAction positive){
        AlertDialog.Builder dialog = newInstance(context , title,content);
        dialog.setPositiveButton(positive.getButtonText(),positive);
        return dialog;
    }

    public static AlertDialog.Builder newInstance(Context context , String title , String content , AlertDialogButtonAction positive , AlertDialogButtonAction negative){
        AlertDialog.Builder dialog = newInstance(context,title,content,positive);
        dialog.setNegativeButton(negative.getButtonText(),negative);
        return dialog;
    }
}

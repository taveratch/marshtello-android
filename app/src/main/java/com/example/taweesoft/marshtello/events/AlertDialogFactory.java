package com.example.taweesoft.marshtello.events;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by TAWEESOFT on 3/14/16 AD.
 */
public class AlertDialogFactory {
    private AlertDialogFactory(){}

    public static Dialog newInstance(Context context, String title, String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        return builder.create();
    }

}

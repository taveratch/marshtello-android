package com.example.taweesoft.marshtello.events;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by TAWEESOFT on 3/31/16 AD.
 */
public class CustomDialogFactory {

    public static Dialog newInstance(Context context,int layout,DialogAction action){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(layout);
        action.blind(dialog);
        return dialog;
    }

}

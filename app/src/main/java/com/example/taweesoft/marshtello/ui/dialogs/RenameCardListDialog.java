package com.example.taweesoft.marshtello.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.taweesoft.marshtello.R;

/**
 * Rename card list dialog class.
 * Created by TAWEESOFT on 3/15/16 AD.
 */
public class RenameCardListDialog {
    /*Attributes*/
    private View.OnClickListener yes,cancel;
    private Context context;

    /**
     * Constructor.
     * @param yes
     * @param cancel
     * @param context
     */
    public RenameCardListDialog(View.OnClickListener yes, View.OnClickListener cancel, Context context) {
        this.yes = yes;
        this.cancel = cancel;
        this.context = context;
    }

    /**
     * Show dialog.
     */
    public void show(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.rename_card_list_dialog_layout);

    }
}

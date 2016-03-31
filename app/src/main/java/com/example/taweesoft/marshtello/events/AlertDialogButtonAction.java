package com.example.taweesoft.marshtello.events;

import android.content.DialogInterface;

/**
 * Created by TAWEESOFT on 3/31/16 AD.
 */
public interface AlertDialogButtonAction extends DialogInterface.OnClickListener {
    String getButtonText();
}

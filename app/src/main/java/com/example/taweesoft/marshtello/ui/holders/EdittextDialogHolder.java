package com.example.taweesoft.marshtello.ui.holders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TAWEESOFT on 3/15/16 AD.
 */
public class EdittextDialogHolder {

    @Bind(R.id.add_btn)
    public Button add_btn;

    @Bind(R.id.cancel_btn)
    public Button cancel_btn;

    @Bind(R.id.tv_rename_card_list)
    public TextView tv_rename_card_list;

    @Bind(R.id.card_list_name_txt)
    public EditText card_list_name_txt;

    public EdittextDialogHolder(View view){
        ButterKnife.bind(this,view);
        setFonts(view.getContext());
    }

    public void setFonts(Context context){
        Typeface bold = Utilities.getBoldFont(context);
        Typeface normal = Utilities.getNormalFont(context);
        Utilities.applyFont(bold, tv_rename_card_list);
        Utilities.applyFont(normal , card_list_name_txt);
        Utilities.applyFont(bold,add_btn,cancel_btn);
    }
}

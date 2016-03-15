package com.example.taweesoft.marshtello.ui.holders;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TAWEESOFT on 3/14/16 AD.
 */
public class AddCommentDialogHolder {

    @Bind(R.id.tv_rename_card_list)
    public TextView tv_add_comment;

    @Bind(R.id.card_list_name_txt)
    public EditText comment_txt;

    @Bind(R.id.cancel_btn)
    public Button cancel_btn;

    @Bind(R.id.add_btn)
    public Button add_btn;

    public AddCommentDialogHolder(View view) {
        ButterKnife.bind(this,view);
    }
}

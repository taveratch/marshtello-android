package com.example.taweesoft.marshtello.ui.holders;

import android.view.View;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Holder of comment in listView in CommentFragment.
 * Created by TAWEESOFT on 3/3/16 AD.
 */
public class CommentCustomHolder {

    /*UI Components.*/
    @Bind(R.id.card_list_name_txt)
    public TextView comment_txt;

    @Bind(R.id.preview_date_txt)
    public TextView date_txt;

    public CommentCustomHolder(View view){
        ButterKnife.bind(this,view);
    }
}

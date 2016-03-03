package com.example.taweesoft.marshtello.Holder;

import android.view.View;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TAWEESOFT on 3/3/16 AD.
 */
public class CommentCustomHolder {

    @Bind(R.id.comment_txt)
    public TextView comment_txt;

    @Bind(R.id.date_txt)
    public TextView date_txt;

    public CommentCustomHolder(View view){
        ButterKnife.bind(this,view);
    }
}

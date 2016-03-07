package com.example.taweesoft.marshtello.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * UI holder of card layout in listView in CardListFragment.
 * Created by TAWEESOFT on 2/29/16 AD.
 */
public class CardCustomAdapterHolder extends RecyclerView.ViewHolder{

    /*UI Components*/
    @Bind(R.id.first_char_txt)
    public TextView first_char_txt;

    @Bind(R.id.card_name_txt)
    public TextView card_name_txt;

    @Bind(R.id.comment_count_txt)
    public TextView comment_count_txt;

    /**
     * Constructor.
     * @param view
     */
    public CardCustomAdapterHolder(View view){
        super(view);
        ButterKnife.bind(this,view);
    }



}

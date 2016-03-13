package com.example.taweesoft.marshtello.ui.holders;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * UI holder of card layout in listView in CardListFragment.
 * Created by TAWEESOFT on 2/29/16 AD.
 */
public class CardCustomAdapterHolder extends RecyclerView.ViewHolder{

    /*UI Components*/
    @Bind(R.id.tag_img)
    public ImageView tag_img;

    @Bind(R.id.card_name_txt)
    public TextView card_name_txt;

    @Bind(R.id.card_count_txt)
    public TextView comment_count_txt;

    @Bind(R.id.date_txt)
    public TextView date_txt;

    /**
     * Constructor.
     * @param view
     */
    public CardCustomAdapterHolder(View view){
        super(view);
        ButterKnife.bind(this, view);
        Typeface normal = Utilities.getNormalFont(view.getContext());
        Utilities.applyFont(normal,card_name_txt,comment_count_txt,date_txt);
    }



}

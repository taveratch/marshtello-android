package com.example.taweesoft.marshtello.ui.holders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Holder in CardDetailFragment.
 * Created by TAWEESOFT on 3/1/16 AD.
 */
public class CardDetailHolder {

    /**
     * UI Components.
     */

    @Bind(R.id.card_name_txt)
    public EditText card_name_txt;

    @Bind(R.id.red_img)
    public ImageView red_img;

    @Bind(R.id.blue_img)
    public ImageView blue_img;


    @Bind(R.id.detail_txt)
    public EditText detail_txt;

    @Bind(R.id.remove_btn)
    public ImageView remove_btn;

    @Bind(R.id.rv)
    public RecyclerView rv;

    @Bind(R.id.back_btn)
    public ImageView back_btn;

    @Bind(R.id.edit_card_name_btn)
    public ImageView edit_card_name_btn;

    @Bind(R.id.edit_detail_btn)
    public ImageView edit_detail_btn;

    @Bind(R.id.add_comment_btn)
    public FloatingActionButton add_comment_btn;

    @Bind(R.id.preview_date_txt)
    public TextView date_txt;

    @Bind(R.id.tv_card)
    TextView tv_comment;

    @Bind(R.id.tv_desc)
    TextView tv_desc;

    @Bind(R.id.tv_tag)
    TextView tv_tag;

    /**
     * Constructor.
     * @param view
     */
    public CardDetailHolder(Context context,View view){
        ButterKnife.bind(this, view);
        Typeface typeface = Utilities.getNormalFont(context);
        Typeface bold = Utilities.getBoldFont(context);
        Utilities.applyFont(typeface, detail_txt, card_name_txt);
        Utilities.applyFont(typeface, date_txt);
        Utilities.applyFont(bold, tv_comment, tv_desc, tv_tag);
    }
}

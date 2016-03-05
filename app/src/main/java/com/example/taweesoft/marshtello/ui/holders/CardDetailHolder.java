package com.example.taweesoft.marshtello.ui.holders;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TAWEESOFT on 3/1/16 AD.
 */
public class CardDetailHolder {

    @Bind(R.id.first_char_txt)
    public TextView first_char_txt;

    @Bind(R.id.card_name_txt)
    public EditText card_name_txt;

    @Bind(R.id.red_img)
    public ImageView red_img;

    @Bind(R.id.blue_img)
    public ImageView blue_img;

    @Bind(R.id.header_layout)
    public RelativeLayout header_layout;

    @Bind(R.id.detail_txt)
    public EditText detail_txt;

    public CardDetailHolder(View view){
        ButterKnife.bind(this,view);
    }
}

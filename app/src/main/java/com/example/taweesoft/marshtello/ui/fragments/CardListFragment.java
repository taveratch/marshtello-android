package com.example.taweesoft.marshtello.ui.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.utils.Constants;
import com.example.taweesoft.marshtello.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TAWEESOFT on 3/13/16 AD.
 */
public class CardListFragment extends Fragment {

    /*UI Components*/
    @Bind(R.id.card_list_name)
    TextView card_list_name_txt;

    @Bind(R.id.tv_name)
    TextView tv_name;

    /*Attributes*/
    private int cardlist_id;
    private CardList cardList;

    public CardListFragment(int cardlist_id) {
        this.cardlist_id = cardlist_id;
        cardList = Constants.cardLists.get(cardlist_id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_list_custom_layout_new,container,false);
        ButterKnife.bind(this, view);
        initComponents(inflater.getContext());
        return view;
    }

    /**
     * Initialize components
     * @param context
     */
    public void initComponents(Context context){
        card_list_name_txt.setText(cardList.getName());
        Typeface normal = Utilities.getNormalFont(context);
        Typeface bold = Utilities.getBoldFont(context);
        Utilities.applyFont(bold,tv_name);
        Utilities.applyFont(normal,card_list_name_txt);
    }

}

package com.example.taweesoft.marshtello.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.utils.DataCenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TAWEESOFT on 3/13/16 AD.
 */
public class CardListFragment extends Fragment {

    /*UI Components*/
    @Bind(R.id.card_list_name)
    TextView card_list_name_txt;

    /*Attributes*/
    private int cardlist_id;
    private CardList cardList;

    public CardListFragment(int cardlist_id) {
        this.cardlist_id = cardlist_id;
        cardList = DataCenter.cardLists.get(cardlist_id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_list_custom_layout_new,container,false);
        ButterKnife.bind(this, view);
        card_list_name_txt.setText(cardList.getName());
        return view;
    }

}

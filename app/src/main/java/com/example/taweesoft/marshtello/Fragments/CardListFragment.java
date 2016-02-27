package com.example.taweesoft.marshtello.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taweesoft.marshtello.Card;
import com.example.taweesoft.marshtello.R;

import java.util.List;

/**
 * Card list fragment. (Used on 3 tabs)
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class CardListFragment extends Fragment {

    /*Card list on DataCenter.*/
    private List<Card> cards;

    /**
     * Constructor.
     * @param cards
     */
    public CardListFragment(List<Card> cards) {
        this.cards = cards;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_list_layout,container,false);

        /**
         * todo
         * Define components of card_list_layout.xml and show the content.
         */

        return view;
    }
}

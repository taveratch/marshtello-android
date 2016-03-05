package com.example.taweesoft.marshtello.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by TAWEESOFT on 3/1/16 AD.
 */
public class CardCommentFragment extends Fragment {

    private int card_id,cardList_id;

    public CardCommentFragment(int cardList_id, int card_id) {
        this.cardList_id = cardList_id;
        this.card_id = card_id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

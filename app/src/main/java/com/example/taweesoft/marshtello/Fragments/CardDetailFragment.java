package com.example.taweesoft.marshtello.Fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taweesoft.marshtello.Model.Card;
import com.example.taweesoft.marshtello.Util.DataCenter;
import com.example.taweesoft.marshtello.Holder.CardDetailHolder;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.Util.Utilities;

import io.realm.Realm;

/**
 * Created by TAWEESOFT on 3/1/16 AD.
 */
public class CardDetailFragment extends Fragment {


    private int card_id , cardList_id;
    private Card card;
    private CardDetailHolder holder;
    public CardDetailFragment(int cardList_id, int card_id) {
        this.card_id = card_id;
        this.cardList_id = cardList_id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        card = DataCenter.cardLists.get(cardList_id).getCards().get(card_id);
        View view = inflater.inflate(R.layout.card_detail_layout,container,false);
        holder = new CardDetailHolder(view);
        setFocusChangeEditText();
        /*Initialize header color*/
        if(card.getTag() == DataCenter.RED_TAG)
            holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
        else if(card.getTag() == DataCenter.BLUE_TAG)
            holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.blue)));

        /*Initialize card details*/
        holder.first_char_txt.setText(card.getName().substring(0,1));
        holder.card_name_txt.setText(card.getName());
        holder.detail_txt.setText(card.getDetail());

        /*set tag's action*/
        setTagAction();
        return view;
    }

    /**
     * Change card tag and header color.
     */
    public void setTagAction(){
        holder.red_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm.getInstance(CardDetailFragment.this.getContext()).executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        card.setTag(DataCenter.RED_TAG);
                    }
                });
                holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
            }
        });

        holder.blue_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm.getInstance(CardDetailFragment.this.getContext()).executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        card.setTag(DataCenter.BLUE_TAG);
                    }
                });

                holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.blue)));
            }
        });
    }

    public void setFocusChangeEditText(){
        holder.card_name_txt.setOnFocusChangeListener(new ChangeFocus());
        holder.detail_txt.setOnFocusChangeListener(new ChangeFocus());
    }

    public void saveData(){
        Realm realm = Realm.getInstance(this.getContext());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                card.setName(holder.card_name_txt.getText().toString());
                card.setDetail(holder.detail_txt.getText().toString());
            }
        });

    }

    class ChangeFocus implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus){
                saveData();
                Utilities.hideKeyboard(CardDetailFragment.this.getActivity(),v);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveData();
    }
}

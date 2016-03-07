package com.example.taweesoft.marshtello.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.ui.holders.CardDetailHolder;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Storage;
import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.utils.DataCenter;
import com.example.taweesoft.marshtello.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Card detail fragment
 * Created by TAWEESOFT on 3/1/16 AD.
 */
public class CardDetailFragment extends Fragment {
    /*UI Components.*/
    @Bind(R.id.remove_btn)
    ImageView remove_btn;

    /*Attributes.*/
    private int card_id , cardList_id;
    private Card card;
    private CardDetailHolder holder;
    private int tag;

    /**
     * Constructor.
     * @param cardList_id as index for cardList in DataCenter.
     * @param card_id as index for card in cardList.
     */
    public CardDetailFragment(int cardList_id, int card_id) {
        this.card_id = card_id;
        this.cardList_id = cardList_id;
    }

    /**
     * Called when this fragment are showing.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*Get real card from DataCenter by using index that passed from previous activity.*/
        card = DataCenter.cardLists.get(cardList_id).getCards().get(card_id);
        tag = card.getTag();

        /*Inflater view.*/
        View view = inflater.inflate(R.layout.card_detail_layout,container,false);
        holder = new CardDetailHolder(view);


        /*Initialize header color*/
        if(card.getTag() == DataCenter.RED_TAG)
            holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
        else if(card.getTag() == DataCenter.BLUE_TAG)
            holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.blue)));

        /*Initialize card details*/
        holder.first_char_txt.setText(card.getName().substring(0,1));
        holder.card_name_txt.setText(card.getName());
        holder.detail_txt.setText(card.getDetail());
        holder.card_name_txt.setOnKeyListener(new onKeyboardEntered());
        holder.detail_txt.setOnKeyListener(new onKeyboardEntered());

        /*Set when focus on edittext is changed*/
        setFocusChangeEditText();
        /*set tag's action*/
        setTagAction();
        /*set delete's action*/
        setDeleteAction();
        return view;
    }

    /**
     * Change card tag and header color.
     */
    public void setTagAction(){
        /*Tag action.*/
        holder.red_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Set current tag*/
                tag = DataCenter.RED_TAG;
                /*Save data to storage.*/
                saveData();
                /*Change header layout's color.*/
                holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
            }
        });

        holder.blue_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = DataCenter.BLUE_TAG;
                saveData();
                holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.blue)));
            }
        });
    }

    /**
     * Set delete card action.
     */
    public void setDeleteAction(){
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Building a dialog.*/
                AlertDialog.Builder builder = new AlertDialog.Builder(CardDetailFragment.this.getContext());
                builder.setTitle("Message");
                builder.setMessage("Remove this card from card list ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Call remove card function from storage.*/
                        Context context = CardDetailFragment.this.getContext();
                        Storage.getInstance(context).removeCard(cardList_id, card_id);
                        /*end this activity.*/
                        CardDetailFragment.this.getActivity().finish();
                    }
                });
                builder.setNegativeButton("No", null);
                /*Create and show a dialog.*/
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * Set on focus changed on edit text.
     */
    public void setFocusChangeEditText(){
        holder.card_name_txt.setOnFocusChangeListener(new ChangeFocus());
        holder.detail_txt.setOnFocusChangeListener(new ChangeFocus());
    }

    /**
     * Save the card's data.
     */
    public void saveData(){
        String name = holder.card_name_txt.getText().toString();
        String detail = holder.detail_txt.getText().toString();
        CardManager.editCard(this.getContext(), card, name, detail,tag);

        /*Sort all cards after edited*/
        CardList cardList = DataCenter.cardLists.get(cardList_id);
        CardManager.sortCard(this.getContext(),cardList);
    }

    /*Action when focus on edittext was changed.*/
    class ChangeFocus implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus){
                saveData();
                Utilities.hideKeyboard(CardDetailFragment.this.getActivity(),v);
            }
        }
    }

    /**
     * Action when click ENTER on keyboard.
     */
    class onKeyboardEntered implements View.OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                saveData();
                return true;
            }
            return false;
        }
    }
}

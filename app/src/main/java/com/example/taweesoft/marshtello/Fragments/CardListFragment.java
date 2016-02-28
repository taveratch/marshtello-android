package com.example.taweesoft.marshtello.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taweesoft.marshtello.Card;
import com.example.taweesoft.marshtello.CardList;
import com.example.taweesoft.marshtello.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Card list fragment. (Used on 3 tabs)
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class CardListFragment extends Fragment {

    private CardList cardList;

    @Bind(R.id.listName_txt)
    TextView listName_txt;

    @Bind(R.id.listName_editText)
    EditText listName_editText;

    /*Unique id for send to NewCardActivity to get real CardList*/
    private int id;
    /**
     * Constructor.
     */
    public CardListFragment(CardList cardList, int id) {
        this.id = id;
        Log.e("CardListFragment ID", id + "");
        this.cardList = cardList;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_list_layout,container,false);
        ButterKnife.bind(this, view);

        /**
         * todo
         * Define components of card_list_layout.xml and show the content.
         */


        /*Hide keyboard when click outside edittext and update card name*/
        listName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                    String listName = listName_editText.getText().toString();
                    if(!listName.equals(""))
                        cardList.setName(listName);
                }
            }
        });


        return view;
    }

    /**
     * Hide keyboard
     * @param view
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

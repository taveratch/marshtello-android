package com.example.taweesoft.marshtello.Fragments;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taweesoft.marshtello.Activities.NewCardActivity;
import com.example.taweesoft.marshtello.Card;
import com.example.taweesoft.marshtello.CardList;
import com.example.taweesoft.marshtello.ListViewCustomAdapter.CardCustomAdapter;
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

    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.listName_editText)
    EditText listName_editText;

    @Bind(R.id.add_card_img)
    ImageView add_card_img;

    @Bind(R.id.remove_img)
    ImageView remove_img;
    
    /*Unique id for send to NewCardActivity to get real CardList*/
    private int id;
    private CardCustomAdapter adapter;
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
        add_card_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CardListFragment.this.getContext(), NewCardActivity.class);
                intent.putExtra("id",id);
                startActivityForResult(intent,1);
            }
        });
        /*Hide keyboard when click outside edittext and update card name*/
        listName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    String listName = listName_editText.getText().toString();
                    if (!listName.equals(""))
                        cardList.setName(listName);
                }
            }
        });

        adapter = new CardCustomAdapter(getContext(),R.layout.custom_card_view_layout,cardList.getCards());
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            adapter.notifyDataSetChanged();
        }
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

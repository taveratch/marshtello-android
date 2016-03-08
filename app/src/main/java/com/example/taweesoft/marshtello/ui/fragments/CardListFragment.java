package com.example.taweesoft.marshtello.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taweesoft.marshtello.events.CustomOnClickListener;
import com.example.taweesoft.marshtello.events.SimpleItemTouchHelperCallback;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.ui.adapters.CardRVCustomAdapter;
import com.example.taweesoft.marshtello.ui.views.CardDetailActivity;
import com.example.taweesoft.marshtello.ui.views.NewCardActivity;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.utils.DataCenter;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Storage;
import com.example.taweesoft.marshtello.utils.Utilities;

import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

/**
 * Card list fragment. (Used on 3 tabs)
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class CardListFragment extends Fragment {

    /*Card List*/
    private CardList cardList;

    /*UI Components*/
    @Bind(R.id.rv)
    RecyclerView rv;

    @Bind(R.id.listName_editText)
    EditText listName_editText;

    @Bind(R.id.add_card_img)
    ImageView add_card_img;

    @Bind(R.id.remove_img)
    ImageView remove_img;

    /*Unique id for send to NewCardActivity to get real CardList*/
    private int cardList_id;
    /*Custom listview adapter*/
    private CardRVCustomAdapter adapter;

    private Observer observer;

    /**
     * Constructor.
     */
    public CardListFragment(CardList cardList, int id , Observer observer) {
        this.cardList_id = id;
        this.cardList = cardList;
        this.observer = observer;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_list_layout, container, false);
        ButterKnife.bind(this, view);

        /*Get real CardList*/
        CardList cardList = DataCenter.cardLists.get(cardList_id);

        /*Set cardlist's name*/
        listName_editText.setText(cardList.getName());

        listName_editText.setOnKeyListener(new onKeyboardEntered());

        /*Set all action*/
        setAddCardAction();
        setFocusActionOnCardListName();
        setRemoveCardAction();

        /*Define adapter for recycler view*/
        adapter = new CardRVCustomAdapter(cardList.getCards(),new RecyclerViewAction());
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new FadeInLeftAnimator());
        rv.getItemAnimator().setChangeDuration(1000);
        /*Attach callback to recycler view.*/
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rv);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*Show newest cards in cardlist*/
        adapter.notifyDataSetChanged();
        /*Save to internal database*/
        Storage.getInstance(this.getContext()).saveData();
    }

    /**
     * When click edittext is focusing then click on cardlist keyboard will hide and save change.
     */
    public void setFocusActionOnCardListName(){
        listName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    /*Hide keyboard if click outside the edittext*/
                    Utilities.hideKeyboard(CardListFragment.this.getActivity(), v); //Hide keyboard
                    setCardListName();

                }
            }
        });
    }


    /**
     * Hide keyboard when click outside edittext and update card name
     * */
    public void setAddCardAction(){
        add_card_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardListFragment.this.getContext(), NewCardActivity.class);
                intent.putExtra("id", cardList_id);
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * Set listview action
     */
    class RecyclerViewAction implements CustomOnClickListener{
        @Override
        public void onClick(View view, int position) {
            Intent intent = new Intent(CardListFragment.this.getContext(), CardDetailActivity.class);
            intent.putExtra("card_id", position);
            intent.putExtra("cardList_id", cardList_id);
            startActivityForResult(intent, 1);
        }
    }

    /**
     * Remove card.
     */
    public void setRemoveCardAction(){
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Build the dialog.*/
                AlertDialog.Builder dialog = new AlertDialog.Builder(CardListFragment.this.getContext());
                dialog.setTitle("Message");
                dialog.setMessage("Delete or clear this card list ?");
                dialog.setPositiveButton("Yes, delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Context context = CardListFragment.this.getContext();
                        CardManager.removeCardList(CardListFragment.this.getContext(),DataCenter.cardLists,cardList_id);
                        observer.update(null, cardList_id);
                    }
                });
                dialog.setNeutralButton("NO", null);
                /*Clear all card in the cardlist.*/
                dialog.setNegativeButton("Just clear all card", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Call clear card from Storage.*/
                        Context context = CardListFragment.this.getContext();
                        CardManager.clearAllCard(context,cardList);
                        observer.update(null, cardList_id);
                    }
                });

                /*Create dialog and show.*/
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();


            }
        });
    }

    /**
     * Set card list's name.
     */
    public void setCardListName(){
        String listName = listName_editText.getText().toString();
        /*Check first if cardlist's name is empty.*/
        if (!listName.equals(""))
            /*Set card name and save to storage immediately*/
            CardManager.setCardListName(CardListFragment.this.getContext(),cardList,listName);
    }

    /**
     * Action for click enter on keyboard.
     * Implemented in Edittext.
     */
    class onKeyboardEntered implements View.OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            /*If button is ENTER*/
            if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                setCardListName();
                return true;
            }
            return false;
        }
    }

}

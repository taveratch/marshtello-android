package com.example.taweesoft.marshtello.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taweesoft.marshtello.Activities.CardDetailActivity;
import com.example.taweesoft.marshtello.Activities.NewCardActivity;
import com.example.taweesoft.marshtello.CardList;
import com.example.taweesoft.marshtello.DataCenter;
import com.example.taweesoft.marshtello.ListViewCustomAdapter.CardCustomAdapter;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.Storage;
import com.example.taweesoft.marshtello.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

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
    private int cardList_id;
    private CardCustomAdapter adapter;
    /**
     * Constructor.
     */
    public CardListFragment(CardList cardList, int id) {
        this.cardList_id = id;
        Log.e("CardListFragment ID", id + "");
        this.cardList = cardList;
        Log.e("CardListName ", cardList.getName());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_list_layout, container, false);
        ButterKnife.bind(this, view);

        CardList cardList = DataCenter.cardLists.get(cardList_id);

        listName_editText.setText(cardList.getName());

        setAddCardAction();
        setFocusActionOnCardListName();
        setListViewAction();

        adapter = new CardCustomAdapter(getContext(),R.layout.custom_card_view_layout,cardList.getCards());
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
        Storage.getInstance().saveData(this.getContext());
    }

    public void setFocusActionOnCardListName(){
        listName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Utilities.hideKeyboard(CardListFragment.this.getActivity(), v); //Hide keyboard
                    final String listName = listName_editText.getText().toString();
                    if (!listName.equals(""))
                        Realm.getInstance(CardListFragment.this.getContext()).executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                cardList.setName(listName);
                            }
                        });

                }
            }
        });
    }


    /*Hide keyboard when click outside edittext and update card name*/
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
    public void setListViewAction(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CardListFragment.this.getContext(), CardDetailActivity.class);
                intent.putExtra("card_id", position);
                intent.putExtra("cardList_id" , cardList_id);
                Log.e("PPPPPPP" , cardList_id + " " + position);
                startActivityForResult(intent,1);
            }
        });
    }



}

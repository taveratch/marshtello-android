package com.example.taweesoft.marshtello.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.models.Comment;
import com.example.taweesoft.marshtello.utils.DataCenter;
import com.example.taweesoft.marshtello.ui.adapters.CommentCustomAdapter;
import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by TAWEESOFT on 3/3/16 AD.
 */
public class CommentFragment extends Fragment {
    /*UI Components*/
    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.comment_txt)
    EditText comment_txt;

    @Bind(R.id.submit_btn)
    ImageView submit_btn;

    /*Attributes*/
    private int cardList_id,casd_id;
    private Card card;
    private CommentCustomAdapter adapter;

    /**
     * Constructor
     * @param cardList_id
     * @param card_id
     */
    public CommentFragment(int cardList_id, int card_id) {
        this.cardList_id = cardList_id;
        this.casd_id = card_id;
        card = DataCenter.cardLists.get(cardList_id).getCards().get(card_id);
    }


    /**
     * Show comment fragment view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_layout,container,false);
        ButterKnife.bind(this,view);
        showData();
        setSubmitAction();
        return view;
    }

    /**
     * Show data in listview;
     */
    public void showData(){
        adapter = new CommentCustomAdapter(this.getContext(),R.layout.comment_custom_layout,card.getComments());
        listView.setAdapter(adapter);
    }

    /**
     * Set submit comment action.
     */
    public void setSubmitAction(){
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentStr = comment_txt.getText().toString();
                final Comment comment = new Comment(commentStr);
                /*Add comment to card by realm's transaction.*/
                Realm.getInstance(CommentFragment.this.getContext()).executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        card.getComments().add(comment);
                    }
                });
                /*notify adapter*/
                adapter.notifyDataSetChanged();
                /*Clear text in edittext*/
                comment_txt.setText("");
            }
        });
    }
}

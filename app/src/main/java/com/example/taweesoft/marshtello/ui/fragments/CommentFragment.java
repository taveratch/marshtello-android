package com.example.taweesoft.marshtello.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.taweesoft.marshtello.events.SimpleItemTouchHelperCallback;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.models.Comment;
import com.example.taweesoft.marshtello.ui.adapters.CommentRVCustomAdapter;
import com.example.taweesoft.marshtello.utils.DataCenter;
import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Comment fragment.
 * Created by TAWEESOFT on 3/3/16 AD.
 */
public class CommentFragment extends Fragment {
    /*UI Components*/
    @Bind(R.id.rv)
    RecyclerView rv;

    @Bind(R.id.comment_txt)
    EditText comment_txt;

    @Bind(R.id.submit_btn)
    ImageView submit_btn;

    /*Attributes*/
    private int cardList_id,casd_id;
    private Card card;
//    private CommentCustomAdapter adapter;
    private AlphaInAnimationAdapter adapter;
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
        CommentRVCustomAdapter adapterRV = new CommentRVCustomAdapter(card.getComments());
//        rv.setAdapter(adapter);
        adapter = new AlphaInAnimationAdapter(adapterRV);
        rv.setItemAnimator(new SlideInLeftAnimator());
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);
    }

    /**
     * Set submit comment action.
     */
    public void setSubmitAction(){
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentStr = comment_txt.getText().toString();
                /*Check if comment is not empty.*/
                if(commentStr.length()>0){
                    Comment comment = new Comment(commentStr);
                    /*Add comment to card by realm's transaction.*/
                    CardManager.addComment(CommentFragment.this.getContext(),card,comment);
                    /*notify adapter*/
                    adapter.notifyItemInserted(card.getComments().size()-1);
                    /*Clear text in edittext*/
                    comment_txt.setText("");
                    /*Scroll RecyclerView to bottom*/
                    rv.scrollToPosition(card.getComments().size()-1);
                }

            }
        });
    }
}

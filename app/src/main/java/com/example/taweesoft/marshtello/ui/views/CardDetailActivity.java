package com.example.taweesoft.marshtello.ui.views;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.ui.adapters.CommentRVCustomAdapter;
import com.example.taweesoft.marshtello.ui.holders.CardDetailHolder;
import com.example.taweesoft.marshtello.utils.DataCenter;
import com.example.taweesoft.marshtello.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CardDetailActivity extends AppCompatActivity {

    /**
     * UI Components
     */
//    @Bind(R.id.tab_layout)
//    TabLayout tabLayout;
//
//    @Bind(R.id.pager)
//    ViewPager pager;

    /*Attributes*/
    private int cardList_id;
    private int card_id;
    /*Attributes.*/
    private Card card;
    private CardDetailHolder holder;
    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail_layout_new);
//        ButterKnife.bind(this);
        holder = new CardDetailHolder(getWindow().getDecorView().getRootView());
        /*Get data from CardListFragment*/
        card_id = getIntent().getIntExtra("card_id",-1);
        cardList_id = getIntent().getIntExtra("cardList_id", -1);
        card = DataCenter.cardLists.get(cardList_id).getCards().get(card_id);
        tag = card.getTag();
        /*Hide action bar.*/
        getSupportActionBar().hide();
//        initialTabs();

        /*Initialize header color*/
//        if(card.getTag() == DataCenter.RED_TAG)
//            holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
//        else if(card.getTag() == DataCenter.BLUE_TAG)
//            holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.blue)));

        /*Initialize card details*/
        holder.card_name_txt.setText(card.getName());
        holder.detail_txt.setText(card.getDetail());
        holder.card_name_txt.setOnKeyListener(new onKeyboardEntered());
        holder.detail_txt.setOnKeyListener(new onKeyboardEntered());

        initialRV();
        /*Set when focus on edittext is changed*/
        setFocusChangeEditText();
        /*set tag's action*/
        setTagAction();
        /*set delete's action*/
        setDeleteAction();

    }

    public void initialRV(){
        holder.rv.setHasFixedSize(true);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        holder.rv.setLayoutManager(llm);
        holder.rv.setAdapter(new CommentRVCustomAdapter(card.getComments()));
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
//                holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
            }
        });

        holder.blue_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = DataCenter.BLUE_TAG;
                saveData();
//                holder.header_layout.setBackground(new ColorDrawable(getResources().getColor(R.color.blue)));
            }
        });
    }

    /**
     * Set delete card action.
     */
    public void setDeleteAction(){
        holder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Building a dialog.*/
                AlertDialog.Builder builder = new AlertDialog.Builder(CardDetailActivity.this);
                builder.setTitle("Message");
                builder.setMessage("Remove this card from card list ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Call remove card function from storage.*/
                        Context context = CardDetailActivity.this;
                        CardManager.removeCard(context, DataCenter.cardLists.get(cardList_id).getCards(), card_id);
                        /*end this activity.*/
                        finish();
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
        CardManager.editCard(CardDetailActivity.this, card, name, detail,tag);

        /*Sort all cards after edited*/
        CardList cardList = DataCenter.cardLists.get(cardList_id);
        CardManager.sortCard(CardDetailActivity.this,cardList);
    }

    /*Action when focus on edittext was changed.*/
    class ChangeFocus implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus){
                saveData();
                Utilities.hideKeyboard(CardDetailActivity.this, v);
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

//    private void initialTabs(){
//        tabLayout.addTab(tabLayout.newTab().setText("Card"));
//        tabLayout.addTab(tabLayout.newTab().setText("Comments"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        //Initial adapter and tablayout.
//        adapter = new CardDetailPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), cardList_id,card_id);
//        pager.setAdapter(adapter);
//        pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                pager.setCurrentItem(tab.getPosition());
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }

    /**
     * Set result back to CardListFragment and destroy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(1);
    }
}

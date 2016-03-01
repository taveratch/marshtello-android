package com.example.taweesoft.marshtello.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taweesoft.marshtello.Card;
import com.example.taweesoft.marshtello.CardList;
import com.example.taweesoft.marshtello.DataCenter;
import com.example.taweesoft.marshtello.Fragments.CardListFragment;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class NewCardActivity extends AppCompatActivity {

    private Card card;

    @Bind(R.id.cardName_txt)
    TextView cardName_txt;

    @Bind(R.id.cardDetail_txt)
    TextView cardDetail_txt;

    @Bind(R.id.red_img)
    ImageView red_img;

    @Bind(R.id.blue_img)
    ImageView blue_img;

    private int tag = DataCenter.RED_TAG;

    private RelativeLayout actionBar_layout;

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        //Binding
        ButterKnife.bind(this);

        /*Initialize custom actionbar*/
        setCustomActionBar();

        /*Set actionbar color.*/
        setActionBarColor();

        /*get id from previous activity.*/
        id = getIntent().getIntExtra("id",-1);

        /*Set img tag action*/
        setTagAction();
    }



    /**
     * Create new card in current cardList.
     */
    public void newCard(){

        String cardName = cardName_txt.getText() + "";
        String cardDetail = cardDetail_txt.getText() + "";
        if(cardName.length()>0) {
            card = new Card(cardName, cardDetail, tag);
            final CardList cardList = DataCenter.cardLists.get(id);
            Realm realm = Realm.getInstance(this);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    cardList.getCards().add(card);
                }
            });

            setResult(1);
            finish();
        }
    }

    public void setActionBarColor(){
        if(tag == DataCenter.RED_TAG) {
            actionBar_layout.setBackgroundColor(getResources().getColor(R.color.red));
            Utilities.setActionBarColor(getSupportActionBar(),getResources().getColor(R.color.red));
            Utilities.setStatusBarColor(this, getResources().getColor(R.color.red));
        }else if (tag == DataCenter.BLUE_TAG) {
            actionBar_layout.setBackgroundColor(getResources().getColor(R.color.blue));
            Utilities.setActionBarColor(getSupportActionBar(), getResources().getColor(R.color.blue));
            Utilities.setStatusBarColor(this,getResources().getColor(R.color.blue));
        }
    }

    public void setTagAction(){
        red_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = DataCenter.RED_TAG;
                setActionBarColor();
            }
        });

        blue_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = DataCenter.BLUE_TAG;
                setActionBarColor();
            }
        });
    }

    public void setCustomActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater inflater = LayoutInflater.from(this);
        View customActionBar = inflater.inflate(R.layout.new_card_actionbar_layout, null);
        ImageView back_img = (ImageView)customActionBar.findViewById(R.id.back_img);
        ImageView done_img = (ImageView)customActionBar.findViewById(R.id.done_img);
        actionBar_layout = (RelativeLayout)customActionBar.findViewById(R.id.actionBar_layout);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        done_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCard();
            }
        });
        actionBar.setCustomView(customActionBar);
        actionBar.setDisplayShowCustomEnabled(true);
    }
}

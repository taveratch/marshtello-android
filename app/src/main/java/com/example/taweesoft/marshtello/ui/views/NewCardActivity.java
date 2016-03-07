package com.example.taweesoft.marshtello.ui.views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.utils.DataCenter;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * New card activity.
 */
public class NewCardActivity extends AppCompatActivity {


    /*UI Components.*/
    @Bind(R.id.cardName_txt)
    TextView cardName_txt;

    @Bind(R.id.cardDetail_txt)
    TextView cardDetail_txt;

    @Bind(R.id.red_img)
    ImageView red_img;

    @Bind(R.id.blue_img)
    ImageView blue_img;

    /*Attributes.*/
    private int tag = DataCenter.RED_TAG;
    private Card card;
    private RelativeLayout actionBar_layout;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        /*Binding ui components.*/
        ButterKnife.bind(this);

        /*Initialize custom actionbar*/
        setCustomActionBar();

        /*Set actionbar color.*/
        setActionBarColor();

        /*get id from CardListFragment's activity.*/
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

        /*Check if card name is not empty.*/
        if(cardName.length()>0) {
            card = new Card(cardName, cardDetail, tag);
            final CardList cardList = DataCenter.cardLists.get(id);
            CardManager.addCard(this, cardList, card);
            setResult(1);
            finish();
        }
    }

    /**
     * Set actionbar's color when select on different tag.
     */
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

    /**
     * Set tag action
     */
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

    /**
     * Set custom actionbar.
     */
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
                /*End this activity.*/
                finish();
            }
        });

        done_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Call newCard().*/
                newCard();
            }
        });
        actionBar.setCustomView(customActionBar);
        actionBar.setDisplayShowCustomEnabled(true);
    }
}

package com.example.taweesoft.marshtello.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taweesoft.marshtello.Card;
import com.example.taweesoft.marshtello.CardList;
import com.example.taweesoft.marshtello.DataCenter;
import com.example.taweesoft.marshtello.Fragments.CardListFragment;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        //Binding
        ButterKnife.bind(this);

        /*Show back button in actionbar.*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Set actionbar color.*/
        setActionBarColor();

        /*get id from previous activity.*/
        id = getIntent().getIntExtra("id",-1);

        /*Set img tag action*/
        setTagAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_card_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_new_card){ /*call newCard()*/
            newCard();
        }else if(id == android.R.id.home){ /*Close the activity*/
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Create new card in current cardList.
     */
    public void newCard(){

        String cardName = cardName_txt.getText() + "";
        String cardDetail = cardDetail_txt.getText() + "";
        if(cardName.length()>0) {
            card = new Card(cardName, cardDetail, tag);
            CardList cardList = DataCenter.cardLists.get(id);
            cardList.addCard(card);
            setResult(1);
            finish();
        }
    }

    public void setActionBarColor(){
        if(tag == DataCenter.RED_TAG) {
            Utilities.setActionBarColor(getSupportActionBar(), getResources().getColor(R.color.red));
            Utilities.setStatusBarColor(this,getResources().getColor(R.color.red));
        }else if (tag == DataCenter.BLUE_TAG) {
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
}

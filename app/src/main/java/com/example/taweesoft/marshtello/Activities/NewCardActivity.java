package com.example.taweesoft.marshtello.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taweesoft.marshtello.Card;
import com.example.taweesoft.marshtello.CardList;
import com.example.taweesoft.marshtello.DataCenter;
import com.example.taweesoft.marshtello.Fragments.CardListFragment;
import com.example.taweesoft.marshtello.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewCardActivity extends AppCompatActivity {

    private Card card;

    @Bind(R.id.cardName_txt)
    TextView cardName_txt;

    @Bind(R.id.cardDetail_txt)
    TextView cardDetail_txt;

    @Bind(R.id.accept_button)
    Button accept_button;

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        id = getIntent().getIntExtra("id",-1);
        //Binding
        ButterKnife.bind(this);
        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (NewCardActivity.this, CardListFragment.class);
                String cardName = cardName_txt.getText()+"";
                String cardDetail = cardDetail_txt.getText()+"";
                card = new Card(cardName, cardDetail, DataCenter.RED_TAG);
                CardList cardList = DataCenter.cardLists.get(id);
                cardList.addCard(card);
                finish();
            }
        });
    }
}

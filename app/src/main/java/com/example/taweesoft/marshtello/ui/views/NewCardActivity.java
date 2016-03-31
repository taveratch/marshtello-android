package com.example.taweesoft.marshtello.ui.views;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taweesoft.marshtello.events.AlertDialogFactory;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.utils.Constants;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    @Bind(R.id.tv_new_card)
    TextView tv_new_card;

    @Bind(R.id.back_btn)
    ImageView back_btn;

    @Bind(R.id.submit_btn)
    ImageView submit_btn;

    @Bind(R.id.preview_tag_img)
    ImageView preview_tag_img;

    @Bind(R.id.preview_card_name_txt)
    TextView preview_card_name_txt;

    @Bind(R.id.preview_date_txt)
    TextView preview_date_txt;

    @Bind(R.id.tv_comment_count)
    TextView tv_comment_count;

    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.tv_details)
    TextView tv_details;

    @Bind(R.id.tv_tag)
    TextView tv_tag;

    /*Attributes.*/
    private int tag = Constants.RED_TAG;
    private Card card;
    private RelativeLayout actionBar_layout;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        /*Binding ui components.*/
        ButterKnife.bind(this);

        getSupportActionBar().hide();


        /*get id from CardListFragment's activity.*/
        id = getIntent().getIntExtra("id",-1);

        /*Set img tag action*/
        setTagAction();
        setTagImageCheck();
        initComponents();
    }



    public void initComponents(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*End this activity.*/
                finish();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Call newCard().*/
                if(cardName_txt.getText().toString().length() > 0) {
                    newCard();
                    sendBack();
                    finish();
                }else{
                    AlertDialogFactory.newInstance(NewCardActivity.this,"Message","Please enter the card's name").show();
                }
            }
        });

        cardName_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                preview_card_name_txt.setText(cardName_txt.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        preview_date_txt.setText(Utilities.getCardDateStr(System.currentTimeMillis()));

        Typeface bold = Utilities.getBoldFont(this);
        Typeface normal = Utilities.getNormalFont(this);
        Utilities.applyFont(bold,tv_new_card,tv_name,tv_details,tv_tag,preview_card_name_txt);
        Utilities.applyFont(normal,cardName_txt,cardDetail_txt);
        Utilities.applyFont(normal,preview_date_txt,tv_comment_count);

    }
    /**
     * Create new card in current cardList.
     */
    public void newCard(){
        String cardName = cardName_txt.getText() + "";
        String cardDetail = cardDetail_txt.getText() + "";
        card = new Card(cardName, cardDetail, tag);
        final CardList cardList = Constants.cardLists.get(id);
        CardManager.addCard(this, cardList, card);
    }

    /**
     * Set actionbar's color when select on different tag.
     */
    public void setTagImageCheck(){
        if(tag == Constants.RED_TAG) {
            red_img.setImageResource(R.drawable.check_red);
            blue_img.setImageResource(0);
            preview_tag_img.setBackground(getResources().getDrawable(R.drawable.red_tag));
        }else if (tag == Constants.BLUE_TAG) {
            red_img.setImageResource(0);
            blue_img.setImageResource(R.drawable.check_blue);
            preview_tag_img.setBackground(getResources().getDrawable(R.drawable.blue_tag));
        }
    }

    /**
     * Set tag action
     */
    public void setTagAction(){
        red_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = Constants.RED_TAG;
                setTagImageCheck();
            }
        });

        blue_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = Constants.BLUE_TAG;
                setTagImageCheck();
            }
        });
    }

    /**
     * Send data back to parent activity.
     */
    public void sendBack(){
        Log.e("Process NewCardActivity" , "SendBack");
        Intent intent = new Intent();
        intent.putExtra("position",id);
        setResult(2,intent);
    }

    @Override
    public void onBackPressed() {
        Log.e("Process NewCardActivity" ,"onBackPressed");
        sendBack();
        super.onBackPressed();
    }
}

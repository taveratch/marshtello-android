package com.example.taweesoft.marshtello.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.taweesoft.marshtello.events.AlertDialogButtonAction;
import com.example.taweesoft.marshtello.events.AlertDialogFactory;
import com.example.taweesoft.marshtello.events.CustomDialogFactory;
import com.example.taweesoft.marshtello.events.DialogAction;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.models.CardList;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.models.Comment;
import com.example.taweesoft.marshtello.ui.adapters.CommentRVCustomAdapter;
import com.example.taweesoft.marshtello.ui.holders.AddCommentDialogHolder;
import com.example.taweesoft.marshtello.ui.holders.CardDetailHolder;
import com.example.taweesoft.marshtello.utils.Constants;
import com.example.taweesoft.marshtello.utils.Utilities;

public class CardDetailActivity extends AppCompatActivity {

    /*Attributes*/
    private int cardList_id;
    private int card_id;
    /*Attributes.*/
    private Card card;
    private CardDetailHolder holder;
    private int tag;
    private CommentRVCustomAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail_layout_new);

        /*Hide keyboard automatically*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        holder = new CardDetailHolder(this,getWindow().getDecorView().getRootView());
        /*Get data from CardListFragment*/
        card_id = getIntent().getIntExtra("card_id",-1);
        cardList_id = getIntent().getIntExtra("cardList_id", -1);
        card = Constants.cardLists.get(cardList_id).getCards().get(card_id);
        tag = card.getTag();

        /*Hide action bar.*/
        getSupportActionBar().hide();
        /*Initialize card details*/
        holder.card_name_txt.setText(card.getName());
        holder.detail_txt.setText(card.getDetail());

        initialRV();
        /*set tag's action*/
        setTag();

        initComponents();

    }

    public void initComponents() {
        holder.back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent data = new Intent();
                data.putExtra("position", cardList_id);
                Log.e("Send DATA Back", data + "");
                setResult(RESULT_OK, data);
                finish();
            }
        });

        holder.edit_card_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.card_name_txt.setFocusableInTouchMode(true);
                holder.card_name_txt.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.card_name_txt, InputMethodManager.SHOW_FORCED);
            }
        });

        holder.edit_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.detail_txt.setFocusableInTouchMode(true);
                holder.detail_txt.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.detail_txt, InputMethodManager.SHOW_FORCED);
            }
        });

        holder.add_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAction action = new CustomDialogAction();
                Dialog dialog = CustomDialogFactory.newInstance(CardDetailActivity.this, R.layout.add_comment_dialog_layout, action);
                dialog.show();
            }
        });

        holder.remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Building a dialog.*/
                AlertDialogButtonAction positive = new DialogPositiveButtonAction("Yes");
                AlertDialogButtonAction negative = new DialogNegativeButtonAction("No");
                String title = "Message";
                String content = "Remove this card from card list ?";
                AlertDialog.Builder builder = AlertDialogFactory.newInstance(CardDetailActivity.this, title, content, positive, negative);
                /*Create and show a dialog.*/
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        /*Tag action.*/
        holder.red_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Set current tag*/
                tag = Constants.RED_TAG;
                /*Save data to storage.*/
                showRedCheck();
                saveData();
            }
        });

        holder.blue_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = Constants.BLUE_TAG;
                showBlueCheck();
                saveData();
            }
        });


        holder.card_name_txt.setOnFocusChangeListener(new ChangeFocus());
        holder.detail_txt.setOnFocusChangeListener(new ChangeFocus());
    }

    public void initialRV(){
        holder.rv.setHasFixedSize(true);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this);
        holder.rv.setLayoutManager(llm);
        commentAdapter = new CommentRVCustomAdapter(card.getComments());
        holder.rv.setAdapter(commentAdapter);
        holder.rv.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Change card tag and header color.
     */
    public void setTag(){
        if(tag == Constants.RED_TAG) {
            showRedCheck();
        } else if (tag == Constants.BLUE_TAG) {
            showBlueCheck();
        }
    }


    /*Show check image in red tag*/
    public void showRedCheck(){
        holder.red_img.setImageResource(R.drawable.check_red);
        holder.blue_img.setImageResource(0);
    }

    /*Show check image in blue tag*/
    public void showBlueCheck(){
        holder.red_img.setImageResource(0);
        holder.blue_img.setImageResource(R.drawable.check_blue);
    }


    /**
     * Save the card's data.
     */
    public void saveData(){
        String name = holder.card_name_txt.getText().toString();
        String detail = holder.detail_txt.getText().toString();
        CardManager.editCard(CardDetailActivity.this, card, name, detail,tag);

        /*Sort all cards after edited*/
        CardList cardList = Constants.cardLists.get(cardList_id);
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
     * Set data back to parent activity
     */
    public void sendBack(){
        Intent data = new Intent();
        data.putExtra("position", cardList_id);
        setResult(1, data);
    }


    @Override
    public void onBackPressed() {
        saveData();
        sendBack();
        super.onBackPressed();
    }

    class DialogPositiveButtonAction implements AlertDialogButtonAction {
        private String buttonText;

        public DialogPositiveButtonAction(String buttonText) {
            this.buttonText = buttonText;
        }

        @Override
        public String getButtonText() {
            return buttonText;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            /*Call remove card function from storage.*/
            Context context = CardDetailActivity.this;
            CardManager.removeCard(context, Constants.cardLists.get(cardList_id).getCards(), card_id);
            /*end this activity.*/
            sendBack();
            finish();
        }
    }

    class DialogNegativeButtonAction implements AlertDialogButtonAction {
        private String buttonText;

        public DialogNegativeButtonAction(String buttonText) {
            this.buttonText = buttonText;
        }

        @Override
        public String getButtonText() {
            return buttonText;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    class CustomDialogAction implements DialogAction {
        @Override
        public void blind(final Dialog dialog) {
            dialog.setTitle("Add comment");
            final EditText editText = new EditText(CardDetailActivity.this);
            View view = LayoutInflater.from(CardDetailActivity.this).inflate(R.layout.add_comment_dialog_layout,null);
            dialog.setContentView(view);
            final AddCommentDialogHolder holder = new AddCommentDialogHolder(view);
            holder.add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        /*Add comment to card.*/
                    Comment comment = new Comment(holder.comment_txt.getText().toString());
                    CardManager.addComment(CardDetailActivity.this, card, comment);
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                        /*Notify adapter when inserted a comment.*/
                    CardDetailActivity.this.holder.rv.scrollToPosition(card.getComments().size()-1);
                    commentAdapter.notifyItemInserted(card.getComments().size() - 1);
                        /*dismiss the dialog.*/
                    dialog.dismiss();
                }
            });
            holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        /*dismiss the dialog.*/
                    dialog.dismiss();
                }
            });

                /*Apply fonts into the components*/
            Typeface bold = Utilities.getBoldFont(CardDetailActivity.this);
            Typeface normal = Utilities.getNormalFont(CardDetailActivity.this);
            Utilities.applyFont(bold,holder.tv_add_comment);
            Utilities.applyFont(normal,holder.comment_txt);
            Utilities.applyFont(bold,holder.add_btn,holder.cancel_btn);
        }
    }
}

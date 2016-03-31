package com.example.taweesoft.marshtello.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.events.AlertDialogButtonAction;
import com.example.taweesoft.marshtello.events.AlertDialogFactory;
import com.example.taweesoft.marshtello.events.CustomOnClickListener;
import com.example.taweesoft.marshtello.events.ItemTouchHelperAdapter;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.models.CustomObservable;
import com.example.taweesoft.marshtello.ui.holders.CardCustomAdapterHolder;
import com.example.taweesoft.marshtello.utils.Constants;
import com.example.taweesoft.marshtello.utils.Utilities;

import java.util.Observer;

import io.realm.RealmList;

/**
 * Card Recycler view adapter.
 * Created by TAWEESOFT on 3/7/16 AD.
 */
public class CardRVCustomAdapter extends RecyclerView.Adapter<CardCustomAdapterHolder> implements ItemTouchHelperAdapter {

    /*Attributes*/
    private RealmList<Card> cards;
    private CustomOnClickListener listener;
    private Context context;
    private CustomObservable observable;

    /**
     * Constructor.
     *
     * @param cards
     * @param listener
     */
    public CardRVCustomAdapter(RealmList<Card> cards, CustomOnClickListener listener) {
        this.cards = cards;
        this.listener = listener;
        observable = new CustomObservable();
    }


    @Override
    public CardCustomAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.custom_card_view_layout, null);
        final CardCustomAdapterHolder holder = new CardCustomAdapterHolder(view);

        /*fire onclick in listener.*/
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, holder.getPosition());
            }
        });

        /*show delete dialog when long click*/
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteDialog(holder.getPosition());
                return false;
            }
        });
        /*Enabled long clickable*/
        view.setLongClickable(true);
        return holder;
    }

    @Override
    public void onBindViewHolder(CardCustomAdapterHolder holder, int position) {
        /*Set UI components.*/
        Card card = cards.get(position);
        holder.card_name_txt.setText(card.getName());
        holder.comment_count_txt.setText(card.getComments().size() + "");
        holder.date_txt.setText(Utilities.getCardDateStr(card.getDate()));
        if (card.getTag() == Constants.RED_TAG)
            holder.tag_img.setImageResource(Constants.red_circle_img);
        if (card.getTag() == Constants.BLUE_TAG)
            holder.tag_img.setImageResource(Constants.blue_circle_img);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    /**
     * Action when swipe the card.
     *
     * @param position
     */
    @Override
    public void onItemDismiss(final int position) {

    }

    public void showDeleteDialog(final int position) {
        String title = "Message";
        String content = "Remove this card ?";
        AlertDialogButtonAction positive = new DialogPositiveAction("Yes",position);
        AlertDialogButtonAction negative = new DialogNegativeAction("No");
        AlertDialog.Builder builder = AlertDialogFactory.newInstance(context,title,content,positive,negative);
        builder.create().show();
    }

    public void addObserver(Observer observer){
        observable.addObserver(observer);
    }

    class DialogPositiveAction implements AlertDialogButtonAction {
        private String buttonText;
        private int position;
        public DialogPositiveAction(String buttonText, int position) {
            this.position = position;
            this.buttonText = buttonText;
        }

        @Override
        public String getButtonText() {
            return buttonText;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            CardManager.removeCard(context, cards, position);
            notifyItemRemoved(position);
            observable.setChanged();
            observable.notifyObservers(Constants.CARD_RV_ADAPTER);
        }
    }

    class DialogNegativeAction implements AlertDialogButtonAction {
        private String buttonText;

        public DialogNegativeAction(String buttonText) {
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

}

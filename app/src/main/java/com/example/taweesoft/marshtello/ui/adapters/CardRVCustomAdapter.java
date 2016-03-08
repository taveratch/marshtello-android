package com.example.taweesoft.marshtello.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.events.CustomOnClickListener;
import com.example.taweesoft.marshtello.events.ItemTouchHelperAdapter;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.ui.holders.CardCustomAdapterHolder;
import com.example.taweesoft.marshtello.utils.DataCenter;

import butterknife.Bind;
import io.realm.RealmList;

/**
 * Card Recycler view adapter.
 * Created by TAWEESOFT on 3/7/16 AD.
 */
public class CardRVCustomAdapter extends RecyclerView.Adapter<CardCustomAdapterHolder> implements ItemTouchHelperAdapter{

    /*Attributes*/
    private RealmList<Card> cards;
    private CustomOnClickListener listener;
    private Context context;

    /**
     * Constructor.
     * @param cards
     * @param listener
     */
    public CardRVCustomAdapter(RealmList<Card> cards, CustomOnClickListener listener) {
        this.cards = cards;
        this.listener = listener;
    }


    @Override
    public CardCustomAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.custom_card_view_layout,null);
        final CardCustomAdapterHolder holder = new CardCustomAdapterHolder(view);

        /*fire onclick in listener.*/
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v,holder.getPosition());
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
        holder.first_char_txt.setText(card.getName().substring(0,1));
        holder.comment_count_txt.setText(card.getComments().size() + "");
        if(card.getTag() == DataCenter.RED_TAG)
            holder.first_char_txt.setBackground(context.getResources().getDrawable(DataCenter.red_circle_img));
        if(card.getTag() == DataCenter.BLUE_TAG)
            holder.first_char_txt.setBackground(context.getResources().getDrawable(DataCenter.blue_circle_img));
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
     * @param position
     */
    @Override
    public void onItemDismiss(final int position) {

    }

    public void showDeleteDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Message");
        builder.setMessage("Remove this card ?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CardManager.removeCard(context, cards, position);
                notifyItemRemoved(position);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notifyDataSetChanged();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                notifyDataSetChanged();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

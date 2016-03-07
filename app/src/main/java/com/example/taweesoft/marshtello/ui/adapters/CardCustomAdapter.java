package com.example.taweesoft.marshtello.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taweesoft.marshtello.models.Card;
import com.example.taweesoft.marshtello.utils.DataCenter;
import com.example.taweesoft.marshtello.ui.holders.CardCustomAdapterHolder;
import com.example.taweesoft.marshtello.R;

import java.util.List;

/**
 * Card Custom adapter for ListView in CardListFragment.
 * Created by TAWEESOFT on 2/29/16 AD.
 */
public class CardCustomAdapter extends ArrayAdapter<Card> {

    /**
     * Constructor.
     * @param context
     * @param resource
     * @param cards
     */
    public CardCustomAdapter(Context context, int resource, List<Card> cards) {
        super(context, resource,cards);
    }


    /**
     * Get current view.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*Using ViewHolder*/
        CardCustomAdapterHolder holder = null;
        /*If view is null*/
        if(convertView == null){
            /*Then create new view and new ViewHolder.*/
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_card_view_layout,null);
            holder = new CardCustomAdapterHolder(convertView);
            /*Set holder to be a tag of this view.*/
            convertView.setTag(holder);
        }else{ /*Otherwise get ViewHolder from view's tag.*/
            holder = (CardCustomAdapterHolder)convertView.getTag();
        }

        /*Set UI components.*/
        Card card = getItem(position);
        holder.card_name_txt.setText(card.getName());
        holder.first_char_txt.setText(card.getName().substring(0,1));
        holder.comment_count_txt.setText(card.getComments().size()+"");
        if(card.getTag() == DataCenter.RED_TAG)
            holder.first_char_txt.setBackground(getContext().getResources().getDrawable(DataCenter.red_circle_img));
        if(card.getTag() == DataCenter.BLUE_TAG)
            holder.first_char_txt.setBackground(getContext().getResources().getDrawable(DataCenter.blue_circle_img));
        return convertView;
    }
}

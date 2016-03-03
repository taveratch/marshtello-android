package com.example.taweesoft.marshtello.ListViewCustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taweesoft.marshtello.Model.Card;
import com.example.taweesoft.marshtello.Util.DataCenter;
import com.example.taweesoft.marshtello.Holder.CardCustomAdapterHolder;
import com.example.taweesoft.marshtello.R;

import java.util.List;

/**
 * Created by TAWEESOFT on 2/29/16 AD.
 */
public class CardCustomAdapter extends ArrayAdapter<Card> {

    public CardCustomAdapter(Context context, int resource, List<Card> cards) {
        super(context, resource,cards);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardCustomAdapterHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_card_view_layout,null);
            holder = new CardCustomAdapterHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (CardCustomAdapterHolder)convertView.getTag();
        }
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

package com.example.taweesoft.marshtello.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taweesoft.marshtello.models.Comment;
import com.example.taweesoft.marshtello.ui.holders.CommentCustomHolder;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.utils.Utilities;

import io.realm.RealmList;

/**
 * Comment adapter used in ListView in CommentFragment.
 * Created by TAWEESOFT on 3/3/16 AD.
 */
public class CommentCustomAdapter extends ArrayAdapter<Comment> {

    /**
     * Constructor.
     * @param context
     * @param resource
     * @param comments
     */
    public CommentCustomAdapter(Context context, int resource, RealmList<Comment> comments) {
        super(context, resource , comments);
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
        /*Use ViewHolder pattern and return the view.*/
        CommentCustomHolder holder =null;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_custom_layout,null);
            holder = new CommentCustomHolder(convertView);
            convertView.setTag(holder);
        }else
            holder = (CommentCustomHolder)convertView.getTag();

        Comment comment = getItem(position);
        holder.comment_txt.setText(comment.getComment());
        holder.date_txt.setText(Utilities.getDateStr(comment.getDate()));
        return convertView;
    }
}

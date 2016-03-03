package com.example.taweesoft.marshtello.ListViewCustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.taweesoft.marshtello.Model.Comment;
import com.example.taweesoft.marshtello.Holder.CommentCustomHolder;
import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.Util.Utilities;

import io.realm.RealmList;

/**
 * Created by TAWEESOFT on 3/3/16 AD.
 */
public class CommentCustomAdapter extends ArrayAdapter<Comment> {

    public CommentCustomAdapter(Context context, int resource, RealmList<Comment> comments) {
        super(context, resource , comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

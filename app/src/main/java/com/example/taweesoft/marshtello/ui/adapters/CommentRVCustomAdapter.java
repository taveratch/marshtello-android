package com.example.taweesoft.marshtello.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.models.Comment;
import com.example.taweesoft.marshtello.utils.Utilities;


import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by TAWEESOFT on 3/7/16 AD.
 */
public class CommentRVCustomAdapter extends RecyclerView.Adapter<CommentRVCustomAdapter.ViewHolder> {

    /*Attributes.*/
    private RealmList<Comment> comments;

    /**
     * Inner class for View holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.comment_txt)
        TextView comment_txt;

        @Bind(R.id.date_txt)
        TextView date_txt;


        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }


    public CommentRVCustomAdapter(RealmList<Comment> comments){
        this.comments = comments;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_custom_layout,null);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.comment_txt.setText(comment.getComment());
        holder.date_txt.setText(Utilities.getDateStr(comment.getDate()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}

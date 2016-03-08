package com.example.taweesoft.marshtello.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.events.ItemTouchHelperAdapter;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.Comment;
import com.example.taweesoft.marshtello.utils.Utilities;


import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Comment RecyclerView adapter.
 * Created by TAWEESOFT on 3/7/16 AD.
 */
public class CommentRVCustomAdapter extends RecyclerView.Adapter<CommentRVCustomAdapter.ViewHolder> {

    /*Attributes.*/
    private RealmList<Comment> comments;
    private Context context;
    /**
     * Inner class for View holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        /*UI Componenets*/
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
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.comment_custom_layout,null);
        final ViewHolder holder = new ViewHolder(view);

        /*Set long click action to view.*/
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.comment_txt.setText(comment.getComment());
        holder.date_txt.setText(Utilities.getDateStr(comment.getDate()));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public void showDeleteDialog(final int position){
        /*Building a dialog.*/
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Message");
        builder.setMessage("Remove this comment ?");
        /*remove comment when click on "YES"*/
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CardManager.removeComment(context, comments, position);
                notifyItemRemoved(position);
            }
        });

        /*Do nothing when Click "No" or dismiss a dialog.*/
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

        /*Create and show a dialog.*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

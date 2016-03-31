package com.example.taweesoft.marshtello.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taweesoft.marshtello.R;
import com.example.taweesoft.marshtello.events.AlertDialogButtonAction;
import com.example.taweesoft.marshtello.events.AlertDialogFactory;
import com.example.taweesoft.marshtello.managers.CardManager;
import com.example.taweesoft.marshtello.models.Comment;
import com.example.taweesoft.marshtello.utils.Utilities;


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
        @Bind(R.id.card_list_name_txt)
        TextView comment_txt;

        @Bind(R.id.preview_date_txt)
        TextView date_txt;


        ViewHolder(Context context, View view){
            super(view);
            ButterKnife.bind(this, view);
            Typeface typeface = Utilities.getNormalFont(context);
            Utilities.applyFont(typeface,comment_txt,date_txt);
        }
    }


    public CommentRVCustomAdapter(RealmList<Comment> comments){
        this.comments = comments;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.comment_custom_layout,null);
        final ViewHolder holder = new ViewHolder(context,view);

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
        String title = "Message";
        String content = "Remove this comment ?";
        AlertDialogButtonAction positive = new DialogPositiveAction("Yes",position);
        AlertDialogButtonAction negative = new DialogNegativeAction("No");
        /*Building a dialog.*/
        AlertDialog.Builder builder = AlertDialogFactory.newInstance(context, title,content,positive,negative);
        /*show dialog*/
        builder.create().show();
    }

    class DialogPositiveAction implements AlertDialogButtonAction {
        private String buttonText;
        private int position;
        public DialogPositiveAction(String buttonText, int position) {
            this.buttonText = buttonText;
            this.position = position;
        }

        @Override
        public String getButtonText() {
            return buttonText;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            CardManager.removeComment(context, comments, position);
            notifyItemRemoved(position);
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
            notifyDataSetChanged();
        }
    }
}

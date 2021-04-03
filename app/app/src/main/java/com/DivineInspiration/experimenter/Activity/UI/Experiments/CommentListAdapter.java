package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Model.Comment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private List<Comment> comments = new ArrayList<>();

    public CommentListAdapter() { super(); }
    public CommentListAdapter(List<Comment> comments) {
        super();
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // TODO ADD .xml for comment_item
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Set comment card text
        holder.getCommenterName().setText(comments.get(position).getCommenterName());
        holder.getCommentText().setText(comments.get(position).getComment());

        // TODO Set onClickListener for replies
    }

    @Override
    public int getItemCount() { return comments.size(); }

    public void setComments(List<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView card;
        private final TextView commenterName;
        private final TextView commentText;

        public ViewHolder(View v) {
            super(v);

            card = v.findViewById(R.id.comment_item_card);
            commenterName = v.findViewById(R.id.commenter_name_item);
            commentText = v.findViewById(R.id.comment_item_text);
        }

        public CardView getCard() { return card; }

        public TextView getCommenterName() { return commenterName; }

        public TextView getCommentText() { return commentText; }
    }
}

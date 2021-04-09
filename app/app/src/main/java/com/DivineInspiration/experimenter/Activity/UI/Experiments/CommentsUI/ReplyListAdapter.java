package com.DivineInspiration.experimenter.Activity.UI.Experiments.CommentsUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Controller.CommentManager;
import com.DivineInspiration.experimenter.Model.Comment.Comment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.ViewHolder> {

    private List<Comment> replies = new ArrayList<>();
    private String experimentID;
    private final CommentManager commentManager = CommentManager.getInstance();

    public ReplyListAdapter(String experimentID) {
        super();
        this.experimentID = experimentID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Comment reply = replies.get(position);

        // Set comment card text
        holder.getCommenterName().setText(reply.getCommenterName());
        holder.getCommentText().setText(reply.getComment());
        holder.getAddReplyButton().setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() { return replies.size(); }

    public void setReplies(List<Comment> replies) {
        this.replies.clear();
        this.replies.addAll(replies);

        notifyDataSetChanged();
    }

    public void addReply(Comment reply) {
        this.replies.add(reply);
        notifyItemInserted(replies.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView card;
        private final TextView commenterName;
        private final TextView commentText;
        private final Button addReplyButton;
        private final Button viewRepliesButton;

        public ViewHolder(View v) {
            super(v);

            card = v.findViewById(R.id.comment_item_card);
            commenterName = v.findViewById(R.id.commenter_name_item);
            commentText = v.findViewById(R.id.comment_item_text);
            addReplyButton = v.findViewById(R.id.add_reply_button);
            viewRepliesButton = v.findViewById(R.id.view_replies_button);
        }

        public CardView getCard() { return card; }

        public TextView getCommenterName() { return commenterName; }

        public TextView getCommentText() { return commentText; }

        public Button getAddReplyButton() { return addReplyButton; }

        public Button getViewRepliesButton() { return viewRepliesButton; }
    }
}

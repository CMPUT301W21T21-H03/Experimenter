package com.DivineInspiration.experimenter.Activity.UI.Comments;

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

/**
 * A custom RecyclerView Adapter class. Displays a list of replies to a comment, represented
 * by a Comment object. Used by
 * {@link com.DivineInspiration.experimenter.Activity.UI.Comments.CommentListAdapter}
 * to create display replies beneath each comment card.
 * @see <a href="https://developer.android.com/guide/topics/ui/layout/recyclerview"> https://developer.android.com/guide/topics/ui/layout/recyclerview </a>
 */
public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.ViewHolder> {

    private List<Comment> replies = new ArrayList<>();
    private final CommentManager commentManager = CommentManager.getInstance();

    /**
     * Constructor
     */
    public ReplyListAdapter() { super(); }

    /**
     * On create
     * @param parent
     * @param viewType
     * @return
     * view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * On bind view
     * @param holder
     * @param position
     * position in adapter
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Comment reply = replies.get(position);

        // Set comment card text
        holder.getCommenterName().setText(reply.getCommenterName());
        holder.getCommentText().setText(reply.getComment());
        holder.getAddReplyButton().setVisibility(View.GONE);
    }

    /**
     * Gets the number of items
     * @return
     * the number of items in comments
     */
    @Override
    public int getItemCount() { return replies.size(); }

    /**
     * Sets data in adapter
     * @param replies
     * the reply list
     */
    public void setReplies(List<Comment> replies) {
        this.replies.clear();
        this.replies.addAll(replies);

        notifyDataSetChanged();
    }

    /**
     * Adds a new reply represented by {@link Comment} to the data list
     * @param reply
     * the added reply
     */
    public void addReply(Comment reply) {
        this.replies.add(reply);
        notifyItemInserted(replies.size() - 1);
    }

    /**
     * View holding all the replies
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView card;
        private final TextView commenterName;
        private final TextView commentText;
        private final Button addReplyButton;

        /**
         * Constructor for replies
         * @param v
         * view
         */
        public ViewHolder(View v) {
            super(v);

            card = v.findViewById(R.id.comment_item_card);
            commenterName = v.findViewById(R.id.commenter_name_item);
            commentText = v.findViewById(R.id.comment_item_text);
            addReplyButton = v.findViewById(R.id.add_reply_button);
        }

        /**
         * Gets the card
         * @return
         * card itself
         */
        public CardView getCard() { return card; }

        /**
         * Gets the name of teh commenter
         * @return
         * the commenter's name
         */
        public TextView getCommenterName() { return commenterName; }

        /**
         * Get the content of the comment
         * @return
         * comment body
         */
        public TextView getCommentText() { return commentText; }

        /**
         * Gets tge reply button
         * @return the reply button
         */
        public Button getAddReplyButton() { return addReplyButton; }
    }
}

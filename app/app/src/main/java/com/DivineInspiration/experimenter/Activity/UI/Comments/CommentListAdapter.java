package com.DivineInspiration.experimenter.Activity.UI.Comments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Controller.CommentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Comment.Comment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A custom RecyclerView Adapter class. Displays a list of comments. Used by
 * {@link com.DivineInspiration.experimenter.Activity.UI.Comments.DiscussionForumFragment}
 * to create display comments.
 * @see <a href="https://developer.android.com/guide/topics/ui/layout/recyclerview"> https://developer.android.com/guide/topics/ui/layout/recyclerview </a>
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> implements CommentManager.OnRepliesReadyListener, CreateReplyDialogFragment.OnReplyCreatedListener {

    private CommentListAdapter thisAdapter;
    private Context context;
    private FragmentManager fragmentManager;
    private List<Comment> comments = new ArrayList<>();
    private Map<String, List<Comment>> replies = new HashMap<>();
    private Map<String, ReplyListAdapter> replyAdapters = new HashMap<>();
    private String experimentID;
    private final CommentManager commentManager = CommentManager.getInstance();

    /**
     * Constructor
     * @param context
     * Current application context. Supply with getContext()
     * @param fragmentManager
     * A {@link FragmentManager} for the current Activity Fragment. Supply with getActivity().getSupportFragmentManager()
     * @param experimentID
     * ID of the experiment whose comments are being listed
     */
    public CommentListAdapter(Context context, FragmentManager fragmentManager, String experimentID) {
        super();
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.experimentID = experimentID;
        thisAdapter = this;
    }

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

        Comment comment = comments.get(position);
        ReplyListAdapter adapter = new ReplyListAdapter();
        replyAdapters.put(comment.getCommentId(), adapter);

        // Set comment card text
        holder.getCommenterName().setText(comment.getCommenterName());
        holder.getCommentText().setText(comment.getComment());

        Log.d("Comment", replyAdapters.toString());

        if (comment.getHasReplies()) {

            commentManager.getCommentReplies(comment.getCommentId(), experimentID, this);
            holder.getReplyList().setVisibility(View.VISIBLE);
            holder.getReplyList().setLayoutManager(new LinearLayoutManager(context));
            holder.getReplyList().setAdapter(adapter);
            holder.getReplyList().addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

            holder.getViewRepliesButton().setVisibility(View.VISIBLE);
        }
        else
        {
            holder.getReplyList().setVisibility(View.GONE);
            holder.getViewRepliesButton().setVisibility(View.GONE);
        }

        holder.getAddReplyButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserManager userManager = UserManager.getInstance();
                Bundle bundle = new Bundle();

                bundle.putString("commenterID", userManager.getLocalUser().getUserId());
                bundle.putString("commenterName", userManager.getLocalUser().getUserName());
                bundle.putString("experimentID", experimentID);
                bundle.putString("commentID", comment.getCommentId());

                CreateReplyDialogFragment dialog = new CreateReplyDialogFragment(thisAdapter);

                dialog.setArguments(bundle);
                dialog.show(fragmentManager, "create comment frag");
            }
        });

        holder.getViewRepliesButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!holder.getRepliesShown()) {

                    holder.changeRepliesShown();
                    holder.getViewRepliesButton().setText("▲ Hide Replies");
                    holder.getReplyList().setVisibility(View.VISIBLE);
                } else {

                    holder.changeRepliesShown();
                    holder.getViewRepliesButton().setText("▼ View Replies");
                    holder.getReplyList().setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * Gets the number of items
     * @return
     * the number of items in comments
     */
    @Override
    public int getItemCount() { return comments.size(); }

    /**
     * Sets data in adapter
     * @param comments
     * the comment list
     */
    public void setComments(List<Comment> comments) {

        this.comments.clear();
        this.replies.clear();
        this.replyAdapters.clear();

        this.comments.addAll(comments);

        this.replies.clear();
        for (Comment comment : comments) {
            this.replies.put(comment.getCommentId(), new ArrayList<>());
        }

        notifyDataSetChanged();
    }

    /**
     * Adds a new {@link Comment} to the data list
     * @param comment
     * the added comment
     */
    public void addComment(Comment comment) {
        this.comments.add(0, comment);
        this.replies.put(comment.getCommentId(), new ArrayList<>());
        notifyItemInserted(0);
    }

    /**
     * This is a interface implementation method. When the requested reply data is ready,
     * CommentManager calls this method and passes the data as a parameter.
     * The method then updates the list of replies being shown for the specified comment
     * @param replies
     * list of replies
     * @param commentID
     * ID of the comment whose replies are being updated.
     */
    @Override
    public void onRepliesReady(List<Comment> replies, String commentID) {

        ReplyListAdapter adapter = this.replyAdapters.get(commentID);

        this.replies.put(commentID, replies);
        adapter.setReplies(replies);
        adapter.notifyDataSetChanged();

        Log.d("Comment", commentID + " Replies: " + replies.toString());
    }

    /**
     * This is a interface implementation method. When a reply is successfully added to the
     * database, CommentManager calls this method and passes that reply as a parameter. The method
     * then adds the new reply to a list of replies being shown for the specified comment
     * @param reply
     * reply to be added
     * @param commentID
     * ID of the comment whose replies are being updated.
     */
    @Override
    public void onReplyAdded(Comment reply, String commentID) {

        replies.get(commentID).add(reply);
        replyAdapters.get(commentID).addReply(reply);   // Dataset update handled in method

        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getCommentId().equals(commentID)) {
                comments.get(i).setHasReplies(true);
                notifyItemChanged(i);
            }
        }
    }

    /**
     * A custom {@link RecyclerView.ViewHolder} class. Displays comments as a card. Also contains
     * a hidden {@link RecyclerView} of replies
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView card;
        private final TextView commenterName;
        private final TextView commentText;
        private final Button addReplyButton;
        private final Button viewRepliesButton;
        private final RecyclerView replyList;
        private boolean repliesShown;

        /**
         * Holder of the view
         * @param v
         * view
         */
        public ViewHolder(View v) {
            super(v);

            card = v.findViewById(R.id.comment_item_card);
            commenterName = v.findViewById(R.id.commenter_name_item);
            commentText = v.findViewById(R.id.comment_item_text);
            addReplyButton = v.findViewById(R.id.add_reply_button);
            viewRepliesButton = v.findViewById(R.id.view_replies_button);
            replyList = v.findViewById(R.id.reply_list);
            repliesShown = false;
        }

        /**
         * Gets the card
         * @return
         * card view
         */
        public CardView getCard() { return card; }

        /**
         * Gets the commenter's name
         * @return
         * commenter's name
         */
        public TextView getCommenterName() { return commenterName; }

        /**
         * Gets the comment text
         * @return
         * the comment content
         */
        public TextView getCommentText() { return commentText; }

        /**
         * Gets the add reply button
         * @return
         * reply button
         */
        public Button getAddReplyButton() { return addReplyButton; }

        /**
         * Gets the reply button
         * @return
         * the reply button
         */
        public Button getViewRepliesButton() { return viewRepliesButton; }

        /**
         * Gets the list of replies
         * @return
         * list of replies
         */
        public RecyclerView getReplyList() { return replyList; }

        /**
         * If the replies are shown to the user
         * @return
         * if the reply is visiable
         */
        public boolean getRepliesShown() { return repliesShown; }

        /**
         * Change if the replies are shown
         */
        public void changeRepliesShown() { repliesShown = !repliesShown; }
    }
}

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

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> implements CommentManager.OnRepliesReadyListener, CreateReplyDialogFragment.OnReplyCreatedListener {

    private CommentListAdapter thisAdapter;
    private Context context;
    private FragmentManager fragmentManager;
    private List<Comment> comments = new ArrayList<>();
    private Map<String, List<Comment>> replies = new HashMap<>();
    private Map<String, ReplyListAdapter> replyAdapters = new HashMap<>();
    private String experimentID;
    private final CommentManager commentManager = CommentManager.getInstance();

    public CommentListAdapter(Context context, FragmentManager fragmentManager, String experimentID) {
        super();
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.experimentID = experimentID;
        thisAdapter = this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Comment comment = comments.get(position);
        ReplyListAdapter adapter = new ReplyListAdapter(experimentID);
        replyAdapters.put(comment.getCommentId(), adapter);

        // Set comment card text
        holder.getCommenterName().setText(comment.getCommenterName());
        holder.getCommentText().setText(comment.getComment());

        Log.d("Comment", replyAdapters.toString());

        if (comment.getHasReplies()) {

            commentManager.getCommentReplies(comment.getCommentId(), experimentID, this);

            holder.getReplyList().setLayoutManager(new LinearLayoutManager(context));
            holder.getReplyList().setAdapter(adapter);
            holder.getReplyList().addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

            holder.getViewRepliesButton().setVisibility(View.VISIBLE);
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

    @Override
    public int getItemCount() { return comments.size(); }

    public void setComments(List<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);

        this.replies.clear();
        for (Comment comment : comments) {
            this.replies.put(comment.getCommentId(), new ArrayList<>());
        }

        notifyDataSetChanged();
        Log.d("Comment", "Num comments: " + comments.size());
        Log.d("Comment", "Comments: " + comments.toString());
        Log.d("Comment", "Replies: " + replies.toString());
        Log.d("Comment", "Comment IDs:");
    }

    @Override
    public void onRepliesReady(List<Comment> replies, String commentID) {

        ReplyListAdapter adapter = this.replyAdapters.get(commentID);

        this.replies.put(commentID, replies);
        adapter.setReplies(replies);
        adapter.notifyDataSetChanged();

        Log.d("Comment", commentID + " Replies: " + replies.toString());
    }

    @Override
    public void onReplyAdded(Comment reply, String commentID) {
        replies.get(commentID).add(reply);
        replyAdapters.get(commentID).setReplies(replies.get(commentID));
        replyAdapters.get(commentID).notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView card;
        private final TextView commenterName;
        private final TextView commentText;
        private final Button addReplyButton;
        private final Button viewRepliesButton;
        private final RecyclerView replyList;
        private boolean repliesShown;

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

        public CardView getCard() { return card; }

        public TextView getCommenterName() { return commenterName; }

        public TextView getCommentText() { return commentText; }

        public Button getAddReplyButton() { return addReplyButton; }

        public Button getViewRepliesButton() { return viewRepliesButton; }

        public RecyclerView getReplyList() { return replyList; }

        public boolean getRepliesShown() { return repliesShown; }

        public void changeRepliesShown() { repliesShown = !repliesShown; }
    }
}

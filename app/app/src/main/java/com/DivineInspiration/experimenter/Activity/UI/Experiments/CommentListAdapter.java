package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Model.Comment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private List<Comment> comments = new ArrayList<>();

    public CommentListAdapter() {}
    public CommentListAdapter(List<Comment> comments) { this.comments = comments; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // TODO ADD .xml for comment_item
         View v = null; // = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // TODO get views for item in holder and write appropriate code
    }

    @Override
    public int getItemCount() { return comments.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // TODO add view objects for each item in the container + getters

        public ViewHolder(View v) {
            super(v);
        }
    }
}

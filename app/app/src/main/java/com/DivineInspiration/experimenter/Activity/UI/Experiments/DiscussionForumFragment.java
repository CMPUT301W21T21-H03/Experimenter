package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.DivineInspiration.experimenter.Controller.CommentManager;
import com.DivineInspiration.experimenter.Model.Comment;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DiscussionForumFragment extends Fragment implements CommentManager.OnCommentsReadyListener {

    private List<Comment> commentList;
    private CommentListAdapter adapter;
    private String experiment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        this.commentList = new ArrayList<>();
        this.adapter = new CommentListAdapter();
        experiment = bundle.getString("experimentID");
        if (experiment == null) { throw new NullPointerException(); }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.discussion_forum_fragment, container, false);

        CommentManager.getInstance().getExperimentComments(experiment, this);

        // Initialize list
        RecyclerView recyclerView = root.findViewById(R.id.comment_list_forum);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        // TODO replies

        // TODO Add comments
        FloatingActionButton addButton = root.findViewById(R.id.add_comment_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

    @Override
    public void onCommentsReady(List<Comment> comments) {
        commentList = comments;
    }
}

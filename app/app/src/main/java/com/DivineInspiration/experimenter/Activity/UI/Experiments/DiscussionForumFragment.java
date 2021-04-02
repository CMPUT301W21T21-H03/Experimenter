package com.DivineInspiration.experimenter.Activity.UI.Experiments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.DivineInspiration.experimenter.Model.Comment;
import com.DivineInspiration.experimenter.R;

import java.util.ArrayList;
import java.util.List;

public class DiscussionForumFragment extends Fragment {

    private List<Comment> commentList;
    private CommentListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.commentList = new ArrayList<>();
        this.adapter = new CommentListAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discussion_forum_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ListView list = view.findViewById(R.id.placeHolderList);

        // TODO Make recycler view with comments. Maybe make only one level of replies?
        // TODO Make comment creation dialog. Steal from experiment creation
        // TODO BACK BUTTON
        // TODO Reply section


    }
}

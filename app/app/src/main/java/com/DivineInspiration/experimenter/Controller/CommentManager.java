package com.DivineInspiration.experimenter.Controller;

import com.DivineInspiration.experimenter.Model.Comment;

import java.util.List;

public class CommentManager {

    public static CommentManager singleton;


    public interface OnCommentsReadyListener {
        void onQueryReady(List<Comment> comments);
    }

    public CommentManager() { }

    public static CommentManager getInstance() {
        return singleton;
    }
}

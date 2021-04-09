package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Controller.CommentManager;
import com.DivineInspiration.experimenter.Model.Comment.Comment;
import com.DivineInspiration.experimenter.Model.Experiment;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

// TODO: Figure out how to connect to firebase locally!!!
// TODO: Add tests for replies

public class TestCommentManager implements CommentManager.OnCommentsReadyListener {




    CommentManager comm_mgr = CommentManager.getInstance();
    List<Comment> comments;

    public void onCommentsReady(List<Comment> comments) {
        this.comments = comments;
    }

    @Test
    public void testAddComment() {
        // Testing addComment & getExperimentComments
        Comment mockComment = new Comment("COMQR44SHQR12M6L0BHLTB", "XDC2BNUY5G", "Adit", new Date(), "No comment test"
        , false, false);
        comm_mgr.addComment(mockComment, "EXPQQRF7SBMYXGOK");
        comm_mgr.getExperimentComments("EXPQQRF7SBMYXGOK", this);
        ArrayList<String> comment_content = new ArrayList<>();
        for (Comment comment : comments) {
            comment_content.add((comment.getComment()));
        }
        assertTrue(comment_content.contains("COMQR44SHQR12M6L0BHLTB"));

        // Test removeComment
        comm_mgr.removeComment("COMQR44SHQR12M6L0BHLTB", "EXPQQRF7SBMYXGOK");
        comm_mgr.getExperimentComments("EXPQQRF7SBMYXGOK", this);
        comment_content = new ArrayList<>();
        for (Comment comment : comments) {
            comment_content.add((comment.getCommentId()));
        }
        assertFalse(comment_content.contains("COMQR44SHQR12M6L0BHLTB"));
    }
}
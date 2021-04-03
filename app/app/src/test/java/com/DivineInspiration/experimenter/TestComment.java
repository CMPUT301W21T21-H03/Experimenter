package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.Comment;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class TestComment {
    private Comment getMockComment() {
        return new Comment("COM1234567",
                "USR123456",
                "Bob Tester",
                new Date(),
                "Testivus");
    }

    @Test
    /* Here, we test the getters and setter of the Comment class */
    public void testCommentGetters() {
        Comment mockComment = getMockComment();
        assertEquals("COM1234567", mockComment.getCommentId());
        assertEquals("USR123456", mockComment.getCommenterId());
        assertEquals("Bob Tester", mockComment.getCommenterName());
        assertEquals("Testivus", mockComment.getComment());

        mockComment.setComment("Testivus has changed");
        assertEquals("Testivus has changed", mockComment.getComment());
    }

    @Test
    /* Here, we test the replies feature for each Comment class */
    public void testReplies() {
        Comment mockComment = getMockComment();

        // Testing getReplies()
        ArrayList<Comment> mockReplies = new ArrayList<>();

        for (int i = 0; i < 3; ++i) {
            Comment reply = getMockComment();
            mockReplies.add(reply);
            mockComment.addReply(reply);
        }

        ArrayList<Comment> replies = mockComment.getReplies();
        for (int i = 0; i < 3; ++i) {
            assertEquals(mockReplies.get(i), replies.get(i));
        }

        // Testing deleteReply()
        Comment reply = mockComment.getReply(2);
        mockComment.deleteReply(reply);
        assertEquals(2, mockComment.getReplies().size());

        replies = mockComment.getReplies();
        for (int i = 0; i < 2; ++i) {
            assertEquals(mockReplies.get(i), replies.get(i));
        }

    }
}
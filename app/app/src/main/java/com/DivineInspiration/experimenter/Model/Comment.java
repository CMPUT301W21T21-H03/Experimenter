package com.DivineInspiration.experimenter.Model;

import java.util.ArrayList;
import java.util.Date;

public class Comment {
    private final User commentator;
    private final Date date;
    private String comment;
    private ArrayList<Comment> replies = new ArrayList<Comment>();

    public Comment(User commentator, String comment) {
        this.commentator = commentator;
        this.comment = comment;
        date = new Date();
    }

    /**
     * Gets the User who made the comment
     * @return: commentator:User
     */
    public User getCommentator() {
        return commentator;
    }

    /**
     * Gets the comment that was made
     * @return: comment:String
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the date the comment was made on
     * @return: date:Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets all the replies to the comment
     * @return: replies:ArrayList<Comment>
     */
    public ArrayList<Comment> getReplies() {
        return replies;
    }

    /**
     * Gets a particular reply to the comment
     * @param: position:Integer
     * @return: reply:Comment
     */
    public Comment getReply(int position) {
        return replies.get(position);
    }

    /**
     * Edits the comment
     * @param : comment:String
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Adds a reply to the comment
     * @param : reply:Comment
     */
    public void addReply(Comment reply) {
        replies.add(reply);
    }

    /**
     * Deletes a reply that was made on the comment. This is an overloaded method
     * @param: reply:Comment
     */
    public void deleteReply(Comment reply) {
        replies.remove(reply);
    }

    /**
     * Deletes a reply that was made on the comment. This is a overloaded method
     * @param: pos:Integer
     */
    public void deleteReply(int position) {
        replies.remove(position);
    }

    /**
     * Returns the name of the commentator and the date it was made. This is an overridden method
     * @return: comment:String
     */
    @Override
    public String toString() {
        return commentator.getUserName() + " | "
                + date.toString().substring(0, 10);
    }
}
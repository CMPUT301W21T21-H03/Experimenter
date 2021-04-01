package com.DivineInspiration.experimenter.Model;

import com.DivineInspiration.experimenter.Controller.UserManager;

import java.util.ArrayList;
import java.util.Date;

public class Comment {

    private final String commentId;
    private final String commenterId;
    private final String commenterName;
    private final Date date;
    private String comment;
    private ArrayList<Comment> replies = new ArrayList<Comment>();

    // TODO Handle replies

    public Comment(String commenterId, String commenterName, String comment) {
        this.commentId = IdGen.genCommentId();
        this.commenterId = commenterId;
        this.commenterName = commenterName;
        this.comment = comment;
        date = new Date();
    }

    public Comment(String commentId, String commenterId, String commenterName, Date date, String comment) {
        this.commentId = IdGen.genCommentId();
        this.commenterId = commenterId;
        this.commenterName = commenterName;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Gets the comment id
     * @return: commentID: String
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * Gets the id of the user who made the comment
     * @return: commenterName: String
     */
    public String getCommenterId() {
        return commenterId;
    }

    /**
     * Gets the name of the user who made the comment
     * @return: commenterName: String
     */
    public String getCommenterName() {
        return commenterName;
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
     * @param : commentString
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

//    /**
//     * Adds a reply to the comment
//     * @param : reply:Comment
//     */
//    public void addReply(Comment reply) {
//        replies.add(reply);
//    }
//
//    /**
//     * Deletes a reply that was made on the comment. This is an overloaded method
//     * @param: reply:Comment
//     */
//    public void deleteReply(Comment reply) {
//        replies.remove(reply);
//    }
//
//    /**
//     * Deletes a reply that was made on the comment. This is a overloaded method
//     * @param: pos:Integer
//     */
//    public void deleteReply(int position) {
//        replies.remove(position);
//    }
//
//    /**
//     * Returns the name of the commentator and the date it was made. This is an overridden method
//     * @return: comment:String
//     */
//    @Override
//    public String toString() {
//        return commenter.getUserName() + " | "
//                + date.toString().substring(0, 10);
//    }
}
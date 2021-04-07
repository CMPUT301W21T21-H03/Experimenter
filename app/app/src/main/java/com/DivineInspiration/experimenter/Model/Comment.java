package com.DivineInspiration.experimenter.Model;

import java.util.Date;

public class Comment {

    private final String commentId;
    private final String commenterId;
    private final String commenterName;
    private final Date date;
    private String comment;

    /**
     * Constructor
     * @param: commentId:String, commenterId:String, commenterName:String, date:Date, comment:String
     */
    public Comment(String commentId, String commenterId, String commenterName, Date date, String comment) {
        this.commentId = commentId;
        this.commenterId = commenterId;
        this.commenterName = commenterName;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Gets the comment id
     * @return: commentID:String
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * Gets the id of the user who made the comment
     * @return: commenterName:String
     */
    public String getCommenterId() {
        return commenterId;
    }

    /**
     * Gets the name of the user who made the comment
     * @return: commenterName:String
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
     * Edits the comment
     * @param : commentString
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
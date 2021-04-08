package com.DivineInspiration.experimenter.Model.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {

    private final String commentId;
    private final String commenterId;
    private final String commenterName;
    private final Date date;
    private String comment;
    private List<Comment> replies;
    private boolean isReply;
    private boolean hasReplies;

    /**
     * Constructor
     * @param: commentId:String, commenterId:String, commenterName:String, date:Date, comment:String
     */
    public Comment(String commentId, String commenterId, String commenterName, Date date, String comment, boolean isReply, boolean hasReplies) {
        this.commentId = commentId;
        this.commenterId = commenterId;
        this.commenterName = commenterName;
        this.date = date;
        this.comment = comment;
        this.replies = new ArrayList<>();
        this.isReply = isReply;
        this.hasReplies = hasReplies;
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
     * Gets all the replies to the comment
     * @return: replies:ArrayList<Comment>
     */
    public List<Comment> getReplies() {
        return replies;
    }

    /**
     * Edits the comment
     * @param : commentString
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isReply() {
        return isReply;
    }

    public boolean getHasReplies() { return hasReplies; }

    public void setHasReplies(boolean hasReplies) { this.hasReplies = hasReplies; }
}
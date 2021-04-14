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
     * @param commentId
     * ID of comment
     * @param commenterId
     * ID of user who commented
     * @param commenterName
     * name of user who commented
     * @param date
     * date of when it was commented
     * @param comment
     * the comment content
     * @param isReply
     * if it is a reply
     * @param hasReplies
     * if it has any replies
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
     * Gets the comment's ID
     * @return
     * ID of comment
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * Gets the id of the user who made the comment
     * @return
     * user ID of the commenter
     */
    public String getCommenterId() {
        return commenterId;
    }

    /**
     * Gets the name of the user who made the comment
     * @return
     * name of user who commented
     */
    public String getCommenterName() {
        return commenterName;
    }

    /**
     * Gets the content of the comment
     * @return
     * body/content of comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the date the comment was made on
     * @return
     * date if when comment was made
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets all the replies to the comment
     * @return
     * a list of all the comments that are replies to this comment
     */
    public List<Comment> getReplies() {
        return replies;
    }

    /**
     * Edit comment
     * @param comment
     * new comment body
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * State of whether this is a reply of a comment
     * @return true if the comment is a reply. false otherwise
     */
    public boolean isReply() {
        return isReply;
    }

    /**
     * If this comment has any replies
     * @return
     * if the comment has any replies
     */
    public boolean getHasReplies() { return hasReplies; }

    /**
     * Sets if this comment has any replies
     * @param hasReplies
     * new state of if the comment has any replies
     */
    public void setHasReplies(boolean hasReplies) { this.hasReplies = hasReplies; }
}
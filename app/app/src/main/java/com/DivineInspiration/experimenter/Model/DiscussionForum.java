package com.DivineInspiration.experimenter.Model;

import java.util.ArrayList;

public class DiscussionForum {
    private ArrayList<Comment> comments;

    /**
     * Constructor: creates an ArrayList to store the comments
     */
    public DiscussionForum() {
        comments = new ArrayList<>();
    }

    /**
     * Adds a comment to the forum
     * @param: comment
     * discussion comment
     */
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    /**
     * Gets all the comments made in the forum
     * @return:
     * discussion comment
     */
    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * Gets a particular comments made in the forum given the position
     * @param position
     * position of the comment
     * @return
     * the comment itself
     */
    public Comment getComment(int position) {
        return comments.get(position);
    }

    /**
     * Removes a particular comments made in the forum given the position. This is an overloaded method.
     * @param:
     * position of comment to be removed
     */
    public void removeComment(int position) {
        comments.remove(position);
    }

    /**
     * Removes a particular comments made in the forum given the Comment object. This is an overloaded method.
     * @param:
     * comment to remove by comment
     */
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
}

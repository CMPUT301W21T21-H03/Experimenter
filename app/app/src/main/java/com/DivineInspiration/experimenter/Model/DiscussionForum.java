package com.DivineInspiration.experimenter.Model;

import java.util.ArrayList;

public class DiscussionForum {
    private ArrayList<Comment> comments;

    /**
     * Constructor: creates an ArrayList to store the comments
     * @param : None
     */
    public DiscussionForum() {
        comments = new ArrayList<>();
    }

    /**
     * Adds a comment to the forum
     * @param: comment:Comment
     */
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    /**
     * Gets all the comments made in the forum
     * @return: comments:ArrayList<Comment>
     */
    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * Gets a particular comments made in the forum given the position
     * @param: position:Integer
     * @return: comments:Comment
     */
    public Comment getComment(int position) {
        return comments.get(position);
    }

    /**
     * Removes a particular comments made in the forum given the position. This is an overloaded method.
     * @param: position:Integer
     */
    public void removeComment(int position) {
        comments.remove(position);
    }

    /**
     * Removes a particular comments made in the forum given the Comment object. This is an overloaded method.
     * @param: comment:Comment
     */
    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
}

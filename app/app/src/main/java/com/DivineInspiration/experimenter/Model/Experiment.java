package com.DivineInspiration.experimenter.Model;

import java.util.ArrayList;
import java.util.UUID;

public class Experiment {
    private String experimentName;
    private  User experimentOwner;
    private  String experimentID;
    private String experimentDescription;
    private ArrayList<User> experimentSubscribers = new ArrayList<User>();
    private DiscussionForum discussionForum = new DiscussionForum();
    private enum Status {ONGOING, CANCELLED, COMPLETE};



    //default constructor
    public Experiment(){
        experimentName = "defaultExpName";
        experimentOwner = null;
        experimentID = "defaultExpID";
        experimentDescription = "defaultDescrip";

    }

    /**
     * Experiment constructor
     * @param experimentName
     * name of experiment
     * @param experimentOwner
     * owner ID of experiment
     * @param experimentDescription
     * description of experiment
     */
    public Experiment(String experimentName, User experimentOwner, String experimentDescription) {

        // TODO Generate id that is not already in the database
        // TODO Get user from id database
        this.experimentName = experimentName;
        this.experimentOwner = experimentOwner;
        this.experimentDescription = experimentDescription;
        this.experimentID = UUID.randomUUID().toString();
    }

    /**
     * Experiment constructor
     * @param experimentName
     * name of experiment
     * @param experimentOwner
     * owner ID of experiment
     * @param experimentDescription
     * description of experiment
     * @param experimentID
     * the id of the experiment
     */
    public Experiment(String experimentName, User experimentOwner, String experimentDescription, String experimentID) {
        // TODO Get user from id database
        this.experimentName = experimentName;
        this.experimentOwner = experimentOwner;
        this.experimentDescription = experimentDescription;
        this.experimentID = experimentID;
    }

    /**
     * Gets name of experiment
     * @return
     * experiment name
     */
    public String getExperimentName() {
        return experimentName;
    }

    /**
     * Sets experiment name
     * @param experimentName
     * name of experiment
     */
    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    /**
     * Gets owner of experiment
     * @return
     * experiment owner
     */
    public User getExperimentOwner() {
        return experimentOwner;
    }

    /**
     * Gets experiment ID
     * @return
     * experiment ID
     */
    public String getExperimentID() {
        return experimentID;
    }

    /**
     * Gets description of experiment
     * @return
     * experiment description
     */
    public String getExperimentDescription() {
        return experimentDescription;
    }

    /**
     * Sets experiment description
     * @param experimentDescription
     * experiment description
     */
    public void setExperimentDescription(String experimentDescription) {
        this.experimentDescription = experimentDescription;
    }

    /**
     * Gets all the subscribers of the experiment
     * @return
     * array of user subscribed to the experiment
     */
    public ArrayList<User> getExperimentSubscribers() {
        return experimentSubscribers;
    }

    /**
     * Adds a subscriber to the list
     * @param subscriber
     * subsciber ID
     */
    public void addSubscriber(User subscriber) {
        experimentSubscribers.add(subscriber);
    }

    /**
     * Removes a subscriber from the list
     * @param subscriber
     * subsciber ID
     */
    public void deleteSubscriber(User subscriber) {
        experimentSubscribers.remove(subscriber);
    }
}
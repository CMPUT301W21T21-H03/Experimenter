package com.DivineInspiration.experimenter;

import java.util.ArrayList;

public class Experiment {
    private String experimentName;
    private String experimentOwnerID;
    private String experimentDescription;
    private ArrayList<String> experimentSubscribers;

    /**
     * Experiment constructor
     * @param experimentName
     * name of experiment
     * @param experimentOwnerID
     * owner ID of experiment
     * @param experimentDescription
     * description of experiment
     */
    public Experiment(String experimentName, String experimentOwnerID, String experimentDescription) {
        this.experimentName = experimentName;
        this.experimentOwnerID = experimentOwnerID;
        this.experimentDescription = experimentDescription;
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
    public String getExperimentOwnerID() {
        return experimentOwnerID;
    }

    // is this nessary? should be fine with just a constructor
//    /**
//     * Sets experiment owner (ID)
//     * @param experimentName
//     * name of experiment's owner
//     */
//    public void setExperimentOwnerID(String experimentOwnerID) {
//        this.experimentOwnerID = experimentOwnerID;
//    }

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
     * @param experimentName
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
    public ArrayList<String> getExperimentSubscribers() {
        return experimentSubscribers;
    }


    /**
     * Adds a subscriber to the list
     * @param subscriberID
     * subsciber ID
     */
    public void addSubscribers(String subscriberID){
        ArrayList<String> experimentSubscribers = getExperimentSubscribers();
        experimentSubscribers.add(subscriberID);
    }

    /**
     * Removes a subscriber from the list
     * @param subscriberID
     * subsciber ID
     */
    public void deleteSubscribers(String subscriberID){
        ArrayList<String> experimentSubscribers = getExperimentSubscribers();
        experimentSubscribers.remove(subscriberID);
    }

}

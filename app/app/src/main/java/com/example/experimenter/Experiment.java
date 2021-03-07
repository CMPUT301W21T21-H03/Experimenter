package com.example.experimenter;

import java.util.ArrayList;

public class Experiment {
    private String experimentName;
    private String experimentOwnerID;
    private String experimentDescription;
    private ArrayList<String> experimentSubscribers;
    //Constructor
    public Experiment(String experimentName, String experimentOwnerID, String experimentDescription) {
        this.experimentName = experimentName;
        this.experimentOwnerID = experimentOwnerID;
        this.experimentDescription = experimentDescription;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getExperimentOwnerID() {
        return experimentOwnerID;
    }

    public void setExperimentOwnerID(String experimentOwnerID) {
        this.experimentOwnerID = experimentOwnerID;
    }

    public String getExperimentDescription() {
        return experimentDescription;
    }

    public void setExperimentDescription(String experimentDescription) {
        this.experimentDescription = experimentDescription;
    }

    public ArrayList<String> getExperimentSubscribers() {
        return experimentSubscribers;
    }
    // Add Subscribers
    public void addSubscribers(String subscriberID){
        ArrayList<String> experimentSubscribers = getExperimentSubscribers();
        experimentSubscribers.add(subscriberID);
    }
    // Delete Subscribers
    public void deleteSubscribers(String subscriberID){
        ArrayList<String> experimentSubscribers = getExperimentSubscribers();
        experimentSubscribers.remove(subscriberID);
    }

}

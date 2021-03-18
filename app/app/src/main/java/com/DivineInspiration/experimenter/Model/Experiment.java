package com.DivineInspiration.experimenter.Model;

import com.DivineInspiration.experimenter.Model.Trial.Trial;

import java.util.ArrayList;
import java.util.UUID;

public class Experiment {
    private String experimentID;
    private String experimentName;
    private  String ownerID;
    private int trialType;
    private String region;
    private int minimumTrials;
    private String experimentDescription;


    public static final int ONGOING = 10;
    public static final int ENDED = 11;
    public static final int HIDDEN = 12; //unpublished

    //default constructor
    public Experiment(){
        experimentName = "defaultExpName";
        ownerID = "null";
        experimentID = "defaultExpID";
        experimentDescription = "defaultDescrip";

    }

    public String getExperimentID() {
        return experimentID;
    }

    public void setExperimentID(String experimentID) {
        this.experimentID = experimentID;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public int getTrialType() {
        return trialType;
    }

    public void setTrialType(int trialType) {
        this.trialType = trialType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getMinimumTrials() {
        return minimumTrials;
    }

    public void setMinimumTrials(int minimumTrials) {
        this.minimumTrials = minimumTrials;
    }

    public String getExperimentDescription() {
        return experimentDescription;
    }

    public void setExperimentDescription(String experimentDescription) {
        this.experimentDescription = experimentDescription;
    }


    /**
     * Experiment constructor
     * @param experimentName
     * name of experiment

     * owner ID of experiment
     * @param experimentDescription
     * description of experiment
     */
    public Experiment(String experimentID, String experimentName, String ownerID, String experimentDescription, int trialType, String region, int minimumTrials) {

        // TODO Generate id that is not already in the database
        // TODO Get user from id database
        this.experimentName = experimentName;
        this.experimentID = experimentID;
        this.ownerID = ownerID;
        this.trialType = trialType;
        this.region = region;
        this.minimumTrials = minimumTrials;
        this.experimentDescription = experimentDescription;


    }



}
package com.DivineInspiration.experimenter.Model;

import com.DivineInspiration.experimenter.Model.Trial.Trial;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class Experiment implements Serializable {

    /**
     * Sorting the experiments by the date in descending order
     */
    public static class sortByDescDate implements Comparator<Experiment> {
        @Override
        public int compare(Experiment o1, Experiment o2) {
            return -(o1.getExperimentID().compareTo(o2.getExperimentID())); // since exp id is time sortable, reverse sorting gives the desired effect
        }
    }

    private String experimentID;
    private String experimentName;
    private String ownerID;
    private String trialType;
    private String region;
    private int minimumTrials;
    private String experimentDescription;
    private boolean requireGeo;
    private String ownerName;
    private String status;

    public static final String ONGOING = "Ongoing";
    public static final String ENDED = "Ended";
    public static final String HIDDEN = "Unpublished";

    /**
     * Constructor that generates experimentId - mainly used for creating existing experiments after retrieval from FireStore
     * @param experimentID
     * ID of experiment
     * @param experimentName
     * name of experiment
     * @param ownerID
     * ID of owner of experiment
     * @param ownerName
     * name of owner
     * @param experimentDescription
     * description of experiment
     * @param trialType
     * type of trial
     * @param region
     * where the experiment took place
     * @param minimumTrials
     * minimum number of trials for it to be "complete"
     * @param requireGeo
     * if it requires geolocation
     * @param status
     * status of experiment (uses the final static strings in this class)
     */
    public Experiment(String experimentID, String experimentName, String ownerID, String ownerName, String experimentDescription, String trialType, String region, int minimumTrials, boolean requireGeo, String status) {
        this.experimentName = experimentName;
        this.experimentID = experimentID;
        this.ownerID = ownerID;
        this.ownerName = ownerName;
        this.trialType = trialType;
        this.region = region;
        this.minimumTrials = minimumTrials;
        this.experimentDescription = experimentDescription;
        this.requireGeo = requireGeo;
        this.status = status;
    }

    /**
     * Constructor that generates experimentId - mainly used for creating new experiments
     * @param experimentName
     * name of experiment
     * @param ownerID
     * ID of owner of experiment
     * @param ownerName
     * name of owner
     * @param experimentDescription
     * description of experiment
     * @param trialType
     * type of trial
     * @param region
     * where the experiment took place
     * @param minimumTrials
     * minimum number of trials for it to be "complete"
     * @param requireGeo
     * if it requires geolocation
     * @param status
     * status of experiment (uses the final static strings in this class)
     */
    public Experiment(String experimentName, String ownerID, String ownerName, String experimentDescription, String trialType, String region, int minimumTrials, boolean requireGeo, String status) {
        this.experimentName = experimentName;
        this.experimentID = IdGen.genExperimentId(ownerID);
        this.ownerID = ownerID;
        this.ownerName = ownerName;
        this.trialType = trialType;
        this.region = region;
        this.minimumTrials = minimumTrials;
        this.experimentDescription = experimentDescription;
        this.requireGeo = requireGeo;
        this.status = status;
    }

    /**
     * Mock object constructor for testing purposes
     */
    public Experiment() {
        experimentName = "defaultExpName";
        ownerName="defaultOwnerName";
        ownerID = "null";
        experimentID = "defaultExpID";
        experimentDescription = "defaultDescrip";
    }

    /**
     * Gets current status of the experiment
     * @return
     * status of experiment
     */
    public String getStatus(){
        return status;
    }

    /**
     * Sets new status of the experiment
     * @param newStatus
     * the new status
     */
    public void setStatus(String newStatus){
        status = newStatus;
    }


    /**
     * Gets the ID of the experiment
     * @return
     * ID of experiment
     */
    public String getExperimentID() {
        return experimentID;
    }

    /**
     * Gets the name of the experiment
     * @return
     * name of the experiment
     */
    public String getExperimentName() {
        return experimentName;
    }

    /**
     * Sets the experiment name
     * @param experimentName
     * new name of the experiment
     */
    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    /**
     * Gets owner ID of experiment
     * @return
     * the owner ID of the experiment
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Gets owner name of the experiment
     * @return
     * name of the owner
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Gets the type of trials the experiment is
     * @return
     * type of experiment
     */
    public String getTrialType() {
        return trialType;
    }

    /**
     * Gets the region of this particular experiment
     * @return
     * the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region of this experiment
     * @param: region:String (the new region)
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the minimum number of trials that the experiment needs
     * @return
     * the minimum number of trials
     */
    public int getMinimumTrials() {
        return minimumTrials;
    }

    /**
     * Sets the minimum number of trials
     * @param minimumTrials
     * new minimum for the number of trials
     */
    public void setMinimumTrials(int minimumTrials) {
        this.minimumTrials = minimumTrials;
    }

    /**
     * Gets experiment description
     * @return
     * description of experiment
     */
    public String getExperimentDescription() {
        return experimentDescription;
    }

    /**
     * Sets a new experiment description
     * @param experimentDescription
     * description of experiment
     */
    public void setExperimentDescription(String experimentDescription) {
        this.experimentDescription = experimentDescription;
    }

    /**
     * Is geological region required for the experiment
     * @return
     * if the geolocation is required
     */
    public boolean isRequireGeo() {
        return requireGeo;
    }

    /**
     * Sets if geological region is required
     * @param requireGeo
     * state of if the geolocation is required
     */
    public void setRequireGeo(boolean requireGeo) {
        this.requireGeo = requireGeo;
    }

    /**
     * Experiment info
     * @return
     * pretty print of important details of the the experiment
     */
    @NotNull
    public String toString(){
        return String.format("%s, %s, %s", experimentName, ownerName, ownerID);
    }
}
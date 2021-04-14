package com.DivineInspiration.experimenter.Model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A class representing an experiment created by a user.
 */
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
     * Constructor (Generates experimentId; mainly used for creating existing experiments after retrieval from FireStore)
     * @param experimentID the ID of the experiment
     * @param experimentName the name of the experiment
     * @param ownerID the owner id of the experiment owner
     * @param ownerName the owner id of the experiment owner
     * @param experimentDescription the description of the experiment
     * @param trialType the type of the trial of the experiment
     * @param region the region where the experiment is happening
     * @param minimumTrials the minimum no. of trials required for the experiment
     * @param requireGeo whether the experiment requires geo-location
     * @param status the current status of the experiment (i.e., on-going, hidden)
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
     * Constructor (Generates experimentId; mainly used for creating existing experiments after retrieval from FireStore)
     * @param experimentName the name of the experiment
     * @param ownerID the owner id of the experiment owner
     * @param ownerName the owner id of the experiment owner
     * @param experimentDescription the description of the experiment
     * @param trialType the type of the trial of the experiment
     * @param region the region where the experiment is happening
     * @param minimumTrials the minimum no. of trials required for the experiment
     * @param requireGeo whether the experiment requires geo-location
     * @param status the current status of the experiment (i.e., on-going, hidden)
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
     * @return status of experiment
     */
    public String getStatus(){
        return status;
    }

    /**
     * Sets a new status for the experiment
     * @param newStatus the new status of the experiment
     */
    public void setStatus(String newStatus){
        status = newStatus;
    }


    /**
     * Gets the ID of the experiment
     * @return the ID of the experiment
     */
    public String getExperimentID() {
        return experimentID;
    }


    /**
     * Gets the name of the experiment
     * @return experiment name
     */
    public String getExperimentName() {
        return experimentName;
    }

    /**
     * Sets a new experiment name
     * @param experimentName experiment name
     */
    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    /**
     * Gets owner ID of experiment
     * @return the owner ID
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Gets owner name of the experiment
     * @return the owner name
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * The type of trials the experiment is for
     * @return type of experiment
     */
    public String getTrialType() {
        return trialType;
    }

    /**
     * Gets the region of this particular experiment
     * @return region of experiment
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region of this experiment
     * @param region the new region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the minimum number of trials that the experiment needs
     * @return the minimum number of trials required for the experiment
     */
    public int getMinimumTrials() {
        return minimumTrials;
    }

    /**
     * Sets the minimum number of trials
     * @param minimumTrials minimum number of trials for the experiment to be considered complete
     */
    public void setMinimumTrials(int minimumTrials) {
        this.minimumTrials = minimumTrials;
    }

    /**
     * gets experiment description
     * @return experiment description
     */
    public String getExperimentDescription() {
        return experimentDescription;
    }

    /**
     * Sets a new experiment description
     * @param experimentDescription description of the experiment
     */
    public void setExperimentDescription(String experimentDescription) {
        this.experimentDescription = experimentDescription;
    }

    /**
     * Is geological region required for the experiment
     * @return whether geo location is required or not
     */
    public boolean isRequireGeo() {
        return requireGeo;
    }

    /**
     * Sets if geological region is required
     * @param requireGeo whether geo location is required or not
     */
    public void setRequireGeo(boolean requireGeo) {
        this.requireGeo = requireGeo;
    }

    /**
     * Experiment info
     * @return pretty print of important details of the the experiment
     */
    @NotNull
    public String toString(){
        return String.format("%s, %s, %s", experimentName, ownerName, ownerID);
    }
}
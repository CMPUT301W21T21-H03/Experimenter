package com.DivineInspiration.experimenter.Model;

import com.DivineInspiration.experimenter.Model.Trial.Trial;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class Experiment implements Serializable {

    public static class sortByDescDate implements Comparator<Experiment> {

        @Override
        public int compare(Experiment o1, Experiment o2) {
            return -(o1.getExperimentID().compareTo(o2.getExperimentID()));//since exp id is time sortable, reverse sorting gives the desired effect
        }
    }

    private String experimentID;
    private String experimentName;
    private  String ownerID;
    private String trialType;
    private String region;
    private int minimumTrials;
    private String experimentDescription;
    private  boolean requireGeo;
    private String ownerName;


    public static final int ONGOING = 10;
    public static final int ENDED = 11;
    public static final int HIDDEN = 12; //unpublished

    /**
     * default constructor when no arguments are given, mostly for testing
     */
    public Experiment() {
        experimentName = "defaultExpName";
        ownerName="defaultOwnerName";
        ownerID = "null";
        experimentID = "defaultExpID";
        experimentDescription = "defaultDescrip";
    }

    @NotNull
    public String toString(){
        return String.format("%s, %s, %s", experimentName, ownerName, ownerID);
    }

    /**
     * Main experiment constructor
     * @param experimentID
     * experiment ID (unique)
     * @param experimentName
     * experiment name
     * @param ownerID
     * owner of the experiment (ID)
     * @param experimentDescription
     * description of the experiment
     * @param trialType
     * type of the trial
     * @param region
     * region on which the experiment is performed
     * @param minimumTrials
     * minimal number of trial for the experiment to be completed
     * @param requireGeo
     * boolean value of if the geolocation is required
     */
    public Experiment(String experimentID, String experimentName, String ownerID,String ownerName, String experimentDescription, String trialType, String region, int minimumTrials, boolean requireGeo) {

        // TODO Generate id that is not already in the database
        // TODO Get user from id database
        this.experimentName = experimentName;
        this.experimentID = experimentID;
        this.ownerID = ownerID;
        this.ownerName = ownerName;
        this.trialType = trialType;
        this.region = region;
        this.minimumTrials = minimumTrials;
        this.experimentDescription = experimentDescription;
        this.requireGeo = requireGeo;
    }

    /**
     * Gets the ID of the experiment
     * @return
     * the ID of the experiment
     */
    public String getExperimentID() {
        return experimentID;
    }

    /**
     * Gets the name of the experiment
     * @return
     * experiment name
     */
    public String getExperimentName() {
        return experimentName;
    }

    /**
     * Sets the experiment name
     * @param experimentName
     * experiment name
     */
    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    /**
     * Gets owner ID of experiment
     * @return
     * the owner ID
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Gets owner Name
     * @return
     * the owner name
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * The type of the experiment
     * @return
     * type of experiment where the mappings are ... TODO:
     */
    public String getTrialType() {
        return trialType;
    }

//    public void setTrialType(int trialType) {
//        this.trialType = trialType;
//    }

    /**
     * Gets the region of this particular experiment
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region of this experiment
     * @param region
     * the new region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the minimum number of trials
     * @return
     * the minimum number of trials
     */
    public int getMinimumTrials() {
        return minimumTrials;
    }

    /**
     * Sets the minimum number of trials
     * @param minimumTrials
     * new minimum
     */
    public void setMinimumTrials(int minimumTrials) {
        this.minimumTrials = minimumTrials;
    }

    /**
     * gets experiment description
     * @return
     * the experiment description string
     */
    public String getExperimentDescription() {
        return experimentDescription;
    }

    /**
     * Sets a new experiment description
     * @param experimentDescription
     * the new experiment description
     */
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
    public Experiment(String experimentName, String ownerID, String ownerName, String experimentDescription, String trialType, String region, int minimumTrials, boolean requireGeo) {

        // TODO Generate id that is not already in the database
        // TODO Get user from id database
        this.experimentName = experimentName;
        this.experimentID = IdGen.genExperimentId(ownerID);
        this.ownerID = ownerID;
        this.ownerName = ownerName;
        this.trialType = trialType;
        this.region = region;
        this.minimumTrials = minimumTrials;
        this.experimentDescription = experimentDescription;
        this.requireGeo = requireGeo;
    }

    /**
     * Geological region required state
     * @return
     * state of geological requirement
     */
    public boolean isRequireGeo() {
        return requireGeo;
    }

    /**
     * Sets if geological region is required
     * @param requireGeo
     */
    public void setRequireGeo(boolean requireGeo) {
        this.requireGeo = requireGeo;
    }
}
package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.Model.User;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public abstract class Trial implements Serializable {

    String trialType;
    String trialID;
    LocalDate trialDate;
    String trialUserID;
    String trialExperimentID;


    GeoPoint location;

    public static final String COUNT = "Count trial";
    public static final String BINOMIAL = "Binomial trial";
    public static final String NONNEGATIVE = "Non-Negative trial";
    public static final String MEASURE = "Measurement trial";

    public Trial(String trialId, String userId, String trialExperimentID , LocalDate date, GeoPoint location){
        this.trialID = trialId;
        this.trialUserID = userId;
        this.trialExperimentID = trialExperimentID;
        this.trialDate = date;
        this.location = location;
    }

    public Trial(String trialId, String userId, String trialExperimentID , LocalDate date){
        this.trialID = trialId;
        this.trialUserID = userId;
        this.trialExperimentID = trialExperimentID;
        this.trialDate = date;
        this.location = null;
    }

    public Trial(String userId, String experimentId){
        this.trialUserID = userId;
        this.trialExperimentID = experimentId;
        this.trialDate = LocalDate.now();
        this.trialID = IdGen.genTrialsId(userId);
        this.location = null;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }


    /**
     * Gets the type of this trial
     * @return
     * The experiment ID
     */
    public String getTrialType() { return trialType; }

    /**
     * Gets the ID of the trial
     * @return
     */
    public String getTrialID() {
        return trialID;
    }

    /**
     * Trial date getter
     * @return
     * gets the trial date as a Java Date class
     */
    public LocalDate getTrialDate() {
        return trialDate;
    }

    /**
     * Gets the person doing the trial
     * @return
     * ID of the trial's user
     */
    public String getTrialUserID() {
        return trialUserID;
    }

    /**
     * Gets the experiment of this trial
     * @return
     * The experiment ID
     */
    public String getTrialExperimentID() {
        return trialExperimentID;
    }

}

package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.IdGen;
import com.DivineInspiration.experimenter.Model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public abstract class Trial implements Serializable {

    boolean ignored = false;
    String trialType;
    String trialID;
    LocalDate trialDate;
    String trialUserID;
    String trialExperimentID;
    String trialOwnerName;

    LatLng location;

    public static final String COUNT = "Count trial";
    public static final String BINOMIAL = "Binomial trial";
    public static final String NONNEGATIVE = "Non-Negative trial";
    public static final String MEASURE = "Measurement trial";

    /**
     * Constructor
     * @param trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, location:GeoPoint
     */
    public Trial(String trialId, String userId, String trialOwnerName, String trialExperimentID, LocalDate date, LatLng location){
        this.trialID = trialId;
        this.trialUserID = userId;
        this.trialOwnerName = trialOwnerName;
        this.trialExperimentID = trialExperimentID;
        this.trialDate = date;
        this.location = location;
    }


    /**
     * Trial constructor - no location data
     * @param trialId
     * unique trial ID
     * @param userId
     * unique user ID
     * @param trialOwnerName
     * name of experimenter that conducted this trial
     * @param trialExperimentID
     * experiment ID of the trial
     * @param date
     * date if when the trial occurred
     */
    public Trial(String trialId, String userId, String trialOwnerName, String trialExperimentID, LocalDate date){
        this.trialID = trialId;
        this.trialUserID = userId;
        this.trialOwnerName = trialOwnerName;
        this.trialExperimentID = trialExperimentID;
        this.trialDate = date;
        this.location = null;
    }


    /**
     * Basic trial constructor
     * @param userId:String (unique user ID)
     * @param trialOwnerName:String (name of experimenter that conducted this trial)
     * @param experimentId:String (unique ID of the experiment)
     */
    public Trial(String userId, String trialOwnerName, String experimentId){
        this.trialUserID = userId;
        this.trialOwnerName = trialOwnerName;
        this.trialExperimentID = experimentId;
        this.trialDate = LocalDate.now();
        this.trialID = IdGen.genTrialsId(userId);
        this.location = null;
    }

    /**
     * If trial is ignored
     * @return :boolean (state of is ignored)
     */
    public boolean isIgnored() {
        return ignored;
    }

    /**
     * Sets if the trial is ignored
     * @param ignored:boolean (new state of ignored in trial)
     */
    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    /**
     * Gets the location data
     * @return
     * location data
     */
    public LatLng getLocation() {
        return location;
    }

    /**
     * Sets the new loaction
     * @param location
     * new location
     */
    public void setLocation(LatLng location) {
        this.location = location;
    }


    /**
     * Gets the type of this trial
     * @return :String (The experiment ID)
     */
    public String getTrialType() { return trialType; }

    /**
     * Gets the ID of the trial
     * @return :String (The trial ID)
     */
    public String getTrialID() {
        return trialID;
    }

    /**
     * Trial date getter
     * @return :LocalDate (gets the trial date as a Java Date class)
     */
    public LocalDate getTrialDate() {
        return trialDate;
    }

    /**
     * Gets the person doing the trial
     * @return :String (ID of the trial's user)
     */
    public String getTrialUserID() {
        return trialUserID;
    }

    /**
     * Gets the experiment of this trial
     * @return :String (The experiment ID)
     */
    public String getTrialExperimentID() {
        return trialExperimentID;
    }

    /**
     * Gets the Experimenter of this trial
     * @return :String (The Owner Name)
     */
    public String getTrialOwnerName() {
        return trialOwnerName;
    }
}
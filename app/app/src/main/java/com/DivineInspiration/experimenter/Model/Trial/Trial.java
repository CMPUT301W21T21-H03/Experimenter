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

    String trialType;
    String trialID;
    LocalDate trialDate;
    String trialUserID;
    String trialExperimentID;
    String trialOwnerName;

    LatLng location;

    public boolean isIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    boolean ignored = false;

    public static final String COUNT = "Count trial";
    public static final String BINOMIAL = "Binomial trial";
    public static final String NONNEGATIVE = "Non-Negative trial";
    public static final String MEASURE = "Measurement trial";




    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, location:GeoPoint
     */
    public Trial(String trialId, String userId, String trialOwnerName ,String trialExperimentID , LocalDate date, LatLng location){
        this.trialID = trialId;
        this.trialUserID = userId;
        this.trialOwnerName = trialOwnerName;
        this.trialExperimentID = trialExperimentID;
        this.trialDate = date;
        this.location = location;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate
     */
    public Trial(String trialId, String userId, String trialOwnerName ,String trialExperimentID , LocalDate date){
        this.trialID = trialId;
        this.trialUserID = userId;
        this.trialOwnerName = trialOwnerName;
        this.trialExperimentID = trialExperimentID;
        this.trialDate = date;
        this.location = null;
    }

    /**
     * Constructor
     * @param: userId:String, trialOwnerName:String, trialExperimentID:String
     */
    public Trial(String userId, String trialOwnerName ,String experimentId){
        this.trialUserID = userId;
        this.trialOwnerName = trialOwnerName;
        this.trialExperimentID = experimentId;
        this.trialDate = LocalDate.now();
        this.trialID = IdGen.genTrialsId(userId);
        this.location = null;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }


    /**
     * Gets the type of this trial
     * @return: :String (The experiment ID)
     */
    public String getTrialType() { return trialType; }

    /**
     * Gets the ID of the trial
     * @return: :String (The trial ID)
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
     * @return: :String (ID of the trial's user)
     */
    public String getTrialUserID() {
        return trialUserID;
    }

    /**
     * Gets the experiment of this trial
     * @return: :String (The experiment ID)
     */
    public String getTrialExperimentID() {
        return trialExperimentID;
    }

    /**
     * Gets the Experimenter of this trial
     * @return: :String (The Owner Name)
     */
    public String getTrialOwnerName() {
        return trialOwnerName;
    }
}
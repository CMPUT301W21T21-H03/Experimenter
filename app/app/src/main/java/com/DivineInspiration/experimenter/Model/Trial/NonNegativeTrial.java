package com.DivineInspiration.experimenter.Model.Trial;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;

/**
 * A class representing an experiment trial completed by a user. Holds a non-negative intger value
 */
public class NonNegativeTrial extends Trial {
    private int count;

    /**
     * The constructor
     * @param trialID
     * ID of trial
     * @param trialUserID
     * ID of user
     * @param trialOwnerName
     * name of the experimenter that did the trial
     * @param trialExperimentID
     * ID of the experiment
     * @param trialDate
     * date of when the trial occurred
     * @param count
     * number of count
     * @param location
     * location of where the trial occurred
     */
    public NonNegativeTrial(String trialID, String trialUserID, String trialOwnerName, String trialExperimentID, LocalDate trialDate, int count, LatLng location){
        super(trialID, trialUserID, trialOwnerName, trialExperimentID, trialDate,location);
        this.trialType = Trial.NONNEGATIVE;
        this.count = count;
    }

    /**
     * The constructor
     * @param trialUserID
     * ID of user
     * @param trialOwnerName
     * name of the experimenter that did the trial
     * @param trialExperimentID
     * ID of the experiment
     * @param count
     * number of count
     * @param location
     * location of where the trial occurred
     */
    public NonNegativeTrial(String trialUserID, String trialOwnerName, String trialExperimentID, int count, LatLng location) {
        super(trialUserID, trialOwnerName, trialExperimentID);
        this.trialType = Trial.NONNEGATIVE;
        this.count = count;
        this.location = location;
    }

    /**
     * The constructor
     * @param trialID
     * ID of trial
     * @param trialUserID
     * ID of user
     * @param trialOwnerName
     * name of the experimenter that did the trial
     * @param trialExperimentID
     * ID of the experiment
     * @param trialDate
     * date of when the trial occurred
     * @param count
     * number of count
     */
    public NonNegativeTrial(String trialID, String trialUserID, String trialOwnerName, String trialExperimentID, LocalDate trialDate, int count) {
        super(trialID, trialUserID,trialOwnerName ,trialExperimentID, trialDate);
        this.trialType = Trial.NONNEGATIVE;
        this.count = count;
    }

    /**
     * Sets count
     * @param count new count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets current count
     * @return count of the trial
     */
    public int getCount() {
        return count;
    }
}
package com.DivineInspiration.experimenter.Model.Trial;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;
import java.util.Random;

/**
 * A class representing an experiment trial completed by a user. Holds a boolean value indicating
 * the success or failure of the trial.
 */
public class BinomialTrial extends Trial {
    private boolean pass;

    /**
     * Constructor
     * @param trialID the trial ID
     * @param trialUserID the user id of the user performing the trial
     * @param trialOwnerName the name of the user performing the trial
     * @param trialExperimentID the experiment id of the experiment the trial is being done for
     * @param trialDate date the trial was carried out
     * @param pass whether the binomial trial passed ot failed
     * @param location the location coordinates of the trial
     */
    public BinomialTrial(String trialID, String trialUserID, String trialOwnerName, String trialExperimentID, LocalDate trialDate, boolean pass, LatLng location){
        super(trialID, trialUserID,trialOwnerName ,trialExperimentID, trialDate,location);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
    }

    /**
     * Constructor
     * @param trialUserID the user id of the user performing the trial
     * @param trialOwnerName the name of the user performing the trial
     * @param trialExperimentID the experiment id of the experiment the trial is being done for
     * @param pass whether the binomial trial passed ot failed
     * @param location the location coordinates of the trial
     */
    public BinomialTrial(String trialUserID,String trialOwnerName ,String trialExperimentID, boolean pass, LatLng location) {
        super(trialUserID, trialOwnerName, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
        this.location = location;
    }

    /**
     * Gets the pass (i.e. the result of the trial)
     * @return pass or fail
     */
    public boolean getPass() {
        return pass;
    }

    /**
     * Sets pass
     * @param newVal new pass
     */
    public void setPass(boolean newVal) {
        pass = newVal;
    }
}
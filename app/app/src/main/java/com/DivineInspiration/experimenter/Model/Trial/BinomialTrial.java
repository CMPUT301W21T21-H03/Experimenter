package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;
import com.google.android.gms.maps.model.LatLng;


import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
     */
    public BinomialTrial(String trialUserID,String trialOwnerName ,String trialExperimentID) {
        super(trialUserID, trialOwnerName, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.pass = false;
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
     * Constructor
     * @param trialID the trial ID
     * @param trialUserID the user id of the user performing the trial
     * @param trialOwnerName the name of the user performing the trial
     * @param trialExperimentID the experiment id of the experiment the trial is being done for
     * @param trialDate date the trial was carried out
     * @param pass whether the binomial trial passed ot failed
     */
    public BinomialTrial(String trialID, String trialUserID, String trialOwnerName, String trialExperimentID, LocalDate trialDate, boolean pass) {
        super(trialID, trialUserID,trialOwnerName ,trialExperimentID, trialDate);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
    }

    /**
     * Mock object constructor for testing purposes
     */
    public BinomialTrial() {
        super("test", "test", "test","test", LocalDate.now().plusDays(new Random().nextInt(70) ));
        this.trialType = Trial.BINOMIAL;
        this.pass = new Random().nextBoolean();
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
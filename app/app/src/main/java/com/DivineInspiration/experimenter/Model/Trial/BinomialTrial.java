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
     * @param pass
     * if it passed or not
     * @param location
     * location of where the trial occurred
     */
    public BinomialTrial(String trialID, String trialUserID, String trialOwnerName, String trialExperimentID, LocalDate trialDate, boolean pass, LatLng location){
        super(trialID, trialUserID,trialOwnerName ,trialExperimentID, trialDate,location);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
    }

    /**
     * The constructor
     * @param trialUserID
     * ID of user
     * @param trialOwnerName
     * name of the experimenter that did the trial
     * @param trialExperimentID
     * ID of the experiment
     */
    public BinomialTrial(String trialUserID,String trialOwnerName ,String trialExperimentID) {
        super(trialUserID, trialOwnerName, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.pass = false;
    }

    /**
     * The constructor
     * @param trialUserID
     * ID of user
     * @param trialOwnerName
     * name of the experimenter that did the trial
     * @param trialExperimentID
     * ID of the experiment
     * @param pass
     * if it passed or not
     * @param location
     * location of where the trial occurred
     */
    public BinomialTrial(String trialUserID,String trialOwnerName ,String trialExperimentID, boolean pass, LatLng location) {
        super(trialUserID, trialOwnerName, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
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
     * @param pass
     * if it passed or not
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
     * Gets the pass
     * @return
     * result of the trial
     */
    public boolean getPass() {
        return pass;
    }

    /**
     * Sets pass state
     * @param newVal
     * new state of pass
     */
    public void setPass(boolean newVal) {
        pass = newVal;
    }
}
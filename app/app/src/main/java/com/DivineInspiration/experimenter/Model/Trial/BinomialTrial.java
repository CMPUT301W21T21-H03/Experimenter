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
     * @param trialID:String
     * @param trialUserID:String
     * @param trialOwnerName:String
     * @param trialExperimentID:String
     * @param trialDate:LocalDate
     * @param pass:boolean
     * @param location:LatLng
     */
    public BinomialTrial(String trialID, String trialUserID, String trialOwnerName, String trialExperimentID, LocalDate trialDate, boolean pass, LatLng location){
        super(trialID, trialUserID,trialOwnerName ,trialExperimentID, trialDate,location);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
    }

    /**
     * Constructor
     * @param trialUserID:String
     * @param trialOwnerName:String
     * @param trialExperimentID:String
     */
    public BinomialTrial(String trialUserID,String trialOwnerName ,String trialExperimentID) {
        super(trialUserID, trialOwnerName, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.pass = false;
    }

    /**
     * Constructor
     * @param trialUserID:String
     * @param trialOwnerName:String
     * @param trialExperimentID:String
     * @param pass:boolean
     * @param location:LatLng
     */
    public BinomialTrial(String trialUserID,String trialOwnerName ,String trialExperimentID, boolean pass, LatLng location) {
        super(trialUserID, trialOwnerName, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
        this.location = location;
    }

    /**
     * Constructor
     * @param trialID:String
     * @param trialUserID:String
     * @param trialOwnerName:String
     * @param trialExperimentID:String
     * @param trialDate:LocalDate
     * @param pass:boolean
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
     * @return pass:boolean
     */
    public boolean getPass() {
        return pass;
    }

    /**
     * Sets pass
     * @param  newVal:boolean (new pass)
     */
    public void setPass(boolean newVal) {
        pass = newVal;
    }
}
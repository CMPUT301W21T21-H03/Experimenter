package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class BinomialTrial extends Trial {
    private boolean pass;

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, count:int, location:GeoPoint
     */
    public BinomialTrial(String trialID, String trialUserID, String trialOwnerName, String trialExperimentID, LocalDate trialDate, boolean pass, GeoPoint location){
        super(trialID, trialUserID,trialOwnerName ,trialExperimentID, trialDate,location);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate
     */
    public BinomialTrial(String trialUserID,String trialOwnerName ,String trialExperimentID) {
        super(trialUserID, trialOwnerName, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.pass = false;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, pass:boolean, location:GeoPoint
     */
    public BinomialTrial(String trialUserID,String trialOwnerName ,String trialExperimentID, boolean pass, GeoPoint location) {
        super(trialUserID, trialOwnerName, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
        this.location = location;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, pass:boolean
     */
    public BinomialTrial(String trialID, String trialUserID, String trialOwnerName, String trialExperimentID, LocalDate trialDate, boolean pass) {
        super(trialID, trialUserID,trialOwnerName ,trialExperimentID, trialDate);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
    }

    /**
     * Mock object constructor for testing purposes
     * @param: void
     */
    public BinomialTrial() {
        super("test", "test", "test","test", LocalDate.now().plusDays(new Random().nextInt(70) ));
        this.trialType = Trial.BINOMIAL;
        this.pass = new Random().nextBoolean();
    }

    /**
     * Gets the pass (i.e. the result of the trial)
     * @return: pass:boolean
     */
    public boolean getPass() {
        return pass;
    }

    /**
     * Sets pass
     * @param: pass:boolean (new pass)
     */
    public void setPass(boolean newVal) {
        pass = newVal;
    }
}
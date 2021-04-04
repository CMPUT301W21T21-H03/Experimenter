package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class BinomialTrial extends Trial {
    private boolean pass;

    //mock constructor
    public BinomialTrial() {

        super("test", "test", "test", LocalDate.now().plusDays(new Random().nextInt(40) - 20));
        this.trialType = Trial.COUNT;
        this.pass = new Random().nextBoolean();
    }

    //constructor with location
    public BinomialTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, boolean pass, GeoPoint location){
        super(trialID, trialUserID, trialExperimentID, trialDate,location);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
    }

    /**
     * Constructor
     *
     * @param trialUserID       user of this trial
     * @param trialExperimentID id of experiment
     */
    public BinomialTrial(String trialUserID, String trialExperimentID) {
        super(trialUserID, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.pass = false;
    }

    /**
     * Constructor
     *
     * @param trialID           the id of this trial
     * @param trialDate         the date of this trial
     * @param trialUserID       user of this trial
     * @param trialExperimentID id of experiment
     */
    public BinomialTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, boolean pass) {
        super(trialID, trialUserID, trialExperimentID, trialDate);
        this.trialType = Trial.BINOMIAL;
        this.pass = pass;
    }

    public boolean getPass() {
        return pass;
    }

    public void setPass(boolean newVal) {
        pass = newVal;
    }

}

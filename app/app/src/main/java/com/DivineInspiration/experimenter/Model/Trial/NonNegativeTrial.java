package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class NonNegativeTrial extends Trial {
    private int count;

    //constructor with location
    public NonNegativeTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, int count, GeoPoint location){
        super(trialID, trialUserID, trialExperimentID, trialDate,location);
        this.trialType = Trial.NONNEGATIVE;
        this.count = count;
    }

    //mock object
    public NonNegativeTrial(){

        super("test", "test", "test", LocalDate.now().plusDays(new Random().nextInt(40) - 20));
        this.trialType = Trial.COUNT;
        Random rng = new Random();

        count = rng.nextInt(40);
    }

    /**
     * Constructor
     * @param trialUserID
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     */
    public NonNegativeTrial(String trialUserID, String trialExperimentID) {
        super(trialUserID, trialExperimentID);
        this.trialType = Trial.NONNEGATIVE;
        this.count = 0;
    }

    /**
     * Constructor
     * @param trialID
     * the id of this trial
     * @param trialDate
     * the date of this trial
     * @param trialUserID
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     * @param count
     * count value of this trial
     */
    public NonNegativeTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, int count) {
        super(trialID, trialUserID, trialExperimentID, trialDate);
        this.trialType = Trial.NONNEGATIVE;
        this.count = count;
    }

    /**
     * Increments count by one
     */
    public void addCount() {
        ++count;
    }

    /**
     * Sets count
     * @param count
     * new count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets current count
     * @return: count
     */
    public int getCount() {
        return count;
    }
}

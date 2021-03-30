package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import java.util.Date;
import java.util.UUID;

public class CountTrial extends Trial {
    private int count;

    /**
     * Constructor
     * @param trialUserID
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     */
    public CountTrial(String trialUserID, String trialExperimentID) {
        this.trialType = Trial.COUNT;
        this.trialID = UUID.randomUUID().toString();
        this.trialDate = new Date();
        this.trialUserID = trialUserID;
        this.trialExperimentID = trialExperimentID;
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
     * count value for this trial
     */
    public CountTrial(String trialID, Date trialDate, String trialUserID, String trialExperimentID, int count) {
        this.trialType = Trial.COUNT;
        this.trialID = trialID;
        this.trialDate = trialDate;
        this.trialUserID = trialUserID;
        this.trialExperimentID = trialExperimentID;
        this.count = count;
    }

    /**
     * Increments count by one
     */
    public void addCount() {
        ++count;
    }

    /**
     * Decrements count by one
     */
    public void decrementCount() {
        --count;
    }

    /**
     * Gets current count
     * @return: count
     */
    public int getCount() {
        return count;
    }
}

package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import java.util.Date;
import java.util.UUID;

public class CountTrial extends Trial {
    private int count;

    /**
     * Constructor
     * @param trialUser
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     */
    public CountTrial(User trialUser, String trialExperimentID) {
        this.trialType = Trial.COUNT;
        this.trialID = UUID.randomUUID().toString();
        this.trialDate = new Date();
        this.trialUser = trialUser;
        this.trialExperimentID = trialExperimentID;
        this.count = 0;
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

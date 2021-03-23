package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import java.util.Date;
import java.util.UUID;

public class NonNegativeTrial extends Trial {
    private int count;

    /**
     * Constructor
     * @param trialUser
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     */
    public NonNegativeTrial(User trialUser, String trialExperimentID) {
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
     * Gets current count
     * @return: count
     */
    public int getCount() {
        return count;
    }
}

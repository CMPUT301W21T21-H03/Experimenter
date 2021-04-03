package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class NonNegativeTrial extends Trial {
    private int count;

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

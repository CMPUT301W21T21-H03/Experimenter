package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;
import java.util.Date;
import java.util.UUID;

public class BinomialTrial extends Trial {
    private int success;
    private int failure;

    /**
     * Constructor
     * @param trialUser
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     */
    public BinomialTrial(User trialUser, String trialExperimentID) {
        this.trialType = Trial.BINOMIAL;
        this.trialID = UUID.randomUUID().toString();
        this.trialDate = new Date();
        this.trialUser = trialUser;
        this.trialExperimentID = trialExperimentID;
        this.success = 0;
        this.failure = 0;
    }

    /**
     * Gets no. of successes
     * @return: success
     */
    public int getSuccess() {
        return success;
    }

    /**
     * Gets no. of failures
     * @return: failure
     */
    public int getFailure() {
        return failure;
    }

    /**
     * Gets total no. of success and failures
     * @return: success + failure
     */
    public int getTotalCount() {
        return success + failure;
    }

    /**
     * Gets the success ratio
     * @return: success / success + failure
     */
    public int getSuccessRatio() {
        if (getTotalCount() == 0)
            return 0;
        int ratio = (int) (((float)success / getTotalCount()) * 100);
        return ratio;
    }

    /**
     * Increments success by one
     */
    public void addSuccess() {
        ++success;
    }

    /**
     * Increments failure by one
     */
    public void addFailure() {
        ++failure;
    }
}

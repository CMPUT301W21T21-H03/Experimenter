package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class BinomialTrial extends Trial {
    private int success;
    private int failure;

    //mock constructor
    public BinomialTrial(){

        super("test", "test", "test", LocalDate.now().plusDays(new Random().nextInt(40) - 20));
        this.trialType = Trial.COUNT;
        this.success =new Random().nextInt(20);
        this.failure =new Random().nextInt(20);
    }

    /**
     * Constructor
     * @param trialUserID
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     */
    public BinomialTrial(String trialUserID, String trialExperimentID) {
        super(trialUserID, trialExperimentID);
        this.trialType = Trial.BINOMIAL;
        this.success = 0;
        this.failure = 0;
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
     * @param success
     * success value for this trial
     * @param failure
     * failure value for this trial
     */
    public BinomialTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, int success, int failure) {
        super(trialID, trialUserID, trialExperimentID, trialDate);
        this.trialType = Trial.BINOMIAL;
        this.success = success;
        this.failure = failure;
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

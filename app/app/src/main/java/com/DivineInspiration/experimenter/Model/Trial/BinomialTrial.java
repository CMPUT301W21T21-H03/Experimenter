package com.DivineInspiration.experimenter.Model.Trial;

import java.util.Date;

public class BinomialTrial extends Trial {
    public int success;
    public int failure;

    public BinomialTrial(String trialID, Date trialDate, String trialUserID, String trialExperimentID, int success, int failure) {
        this.trialID = trialID;
        this.trialDate = trialDate;
        this.trialUserID = trialUserID;
        this.trialExperimentID = trialExperimentID;
        this.success = success;
        this.failure = failure;
    }
}

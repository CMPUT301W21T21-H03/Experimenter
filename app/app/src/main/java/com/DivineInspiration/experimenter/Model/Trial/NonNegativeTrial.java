package com.DivineInspiration.experimenter.Model.Trial;

import java.util.Date;

public class NonNegativeTrial extends Trial {
    public int count;

    public NonNegativeTrial(String trialID, Date trialDate, String trialUserID, String trialExperimentID, int count) {
        this.trialID = trialID;
        this.trialDate = trialDate;
        this.trialUserID = trialUserID;
        this.trialExperimentID = trialExperimentID;
        this.count = count;
    }
}

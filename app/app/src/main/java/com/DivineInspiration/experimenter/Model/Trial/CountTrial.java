package com.DivineInspiration.experimenter.Model.Trial;

import java.util.Date;

public class CountTrial extends Trial {
    // what is object?
//    public String object;
    public int count;

    public CountTrial(String trialID, Date trialDate, String trialUserID, String trialExperimentID, int count) {
        this.trialID = trialID;
        this.trialDate = trialDate;
        this.trialUserID = trialUserID;
        this.trialExperimentID = trialExperimentID;
//        this.object = object;
        this.count = count;
    }
}

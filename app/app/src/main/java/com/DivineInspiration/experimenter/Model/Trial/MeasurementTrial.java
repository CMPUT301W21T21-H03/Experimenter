package com.DivineInspiration.experimenter.Model.Trial;

import java.util.Date;

public class MeasurementTrial extends Trial {
    public float measure;

    public MeasurementTrial(String trialID, Date trialDate, String trialUserID, String trialExperimentID, float measure) {
        this.trialID = trialID;
        this.trialDate = trialDate;
        this.trialUserID = trialUserID;
        this.trialExperimentID = trialExperimentID;
        this.measure = measure;
    }
}

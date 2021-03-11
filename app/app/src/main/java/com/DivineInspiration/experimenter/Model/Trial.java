package com.DivineInspiration.experimenter.Model;

import java.util.Date;

public class Trial {
    private String trialID;
    private Date trialDate;
//    private String trialDescription;
    private String trialUserID;
    private String trialExperimentID;

    /**
     * Gets the ID of the trial
     * @return
     */
    public String getTrialID() {
        return trialID;
    }

    /**
     * Trial date getter
     * @return
     * gets the trial date as a Java Date class
     */
    public Date getTrialDate() {
        return trialDate;
    }

//    /**
//     * Gets the trial description
//     * @return
//     * description of trial
//     */
//    public String getTrialDescription() {
//        return trialDescription;
//    }

    /**
     * Gets the person doing the trial
     * @return
     * ID of the trial's user
     */
    public String getTrialUserID() {
        return trialUserID;
    }

    /**
     * Gets the experiment of this trial
     * @return
     * The experiment ID
     */
    public String getTrialExperimentID() {
        return trialExperimentID;
    }
}

package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;
import java.util.Date;
import java.util.Map;

public abstract class Trial {
    String trialID;
    Date trialDate;
    User trialUser;
    String trialExperimentID;

    public static final String COUNT = "Count trial";
    public static final String BINOMIAL = "Binomial trial";
    public static final String NONNEGATIVE = "Non nagative trial";
    public static final String MEASURE = "Measurement trial";

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

    /**
     * Gets the person doing the trial
     * @return
     * ID of the trial's user
     */
    public User getTrialUser() {
        return trialUser;
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

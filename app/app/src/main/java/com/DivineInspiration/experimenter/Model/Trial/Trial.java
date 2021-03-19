package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;
import java.util.Date;

public abstract class Trial {
    String trialID;
    Date trialDate;
    User trialUser;
    String trialExperimentID;

    public static final int COUNT = 0;
    public static final int BINOMIAL = 1;
    public static final int NONNEGATIVE = -1;
    public static final int MEASURE = 3;


    public static final String[] Type2String = {"Count Trial", "Binomial Trial", "Non Negative Trial", "Measurement Trial"};
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

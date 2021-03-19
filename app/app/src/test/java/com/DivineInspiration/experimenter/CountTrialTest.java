package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.Model.UserContactInfo;

public class CountTrialTest {

    private CountTrial mockCountTrial() {
        User user = mockTrialOwner();
        Experiment experiment = mockExperiment();
        return new CountTrial(user, experiment.getExperimentID());

    }

    private Experiment mockExperiment() {
        Experiment experiment = new Experiment("EXPQQ7FKJB9CVDUE", "Pass or Fail",
                mockExperimentOwner().getUserId() , "keeps count if you failed or passed",
                0, "Edmonton", 10, true);
        return experiment;
    }
    private User mockTrialOwner() {
        User user = new User("Edmonton", "XDC23ABC9K", mockContactInfo(), "test description!!!!");
        return user;
    }
    private User mockExperimentOwner() {
        User user = new User("Calgary", "XDC23DEF9K", mockContactInfo(), "aoooga");
        return user;
    }

    private UserContactInfo mockContactInfo() {
        return new UserContactInfo("Edmonton", "abcd@gmail.com");
    }
}

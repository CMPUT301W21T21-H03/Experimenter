package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.Model.UserContactInfo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NonNegativeTrialTest {

    private NonNegativeTrial mockNonNegativeTrial() {
        User user = mockTrialOwner();
        Experiment experiment = mockExperiment();
        return new NonNegativeTrial(user.getUserId(), experiment.getExperimentID());
    }


    private Experiment mockExperiment() {
        User user = mockExperimentOwner();
        Experiment experiment = new Experiment("EXPQQ7FKJB9CVDUE", "Pass or Fail",
                user.getUserId(), user.getUserName(), "keeps count if you failed or passed",
                "Non nagative trial", "Edmonton", 10, true, Experiment.ONGOING);
        return experiment;
    }
    private User mockTrialOwner() {
        User user = new User("Sheldon", "XDC23ABC9K", mockContactInfo(), "test description!!!!");
        return user;
    }
    private User mockExperimentOwner() {
        User user = new User("Bob", "XDC23DEF9K", mockContactInfo(), "aoooga");
        return user;
    }

    private UserContactInfo mockContactInfo() {
        return new UserContactInfo("Edmonton", "abcd@gmail.com");
    }

    @Test
    public void addCountTest() {
        NonNegativeTrial trial = mockNonNegativeTrial();
        assertEquals(0, trial.getCount());

        trial.addCount();
        assertEquals(1, trial.getCount());

        for (int i = 0; i < 100; i++) {
            trial.addCount();
        }
        assertEquals(101, trial.getCount());

    }
}

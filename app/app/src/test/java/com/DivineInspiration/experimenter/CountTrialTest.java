package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.Model.UserContactInfo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountTrialTest {

    private CountTrial mockCountTrial() {
        User user = mockTrialOwner();
        Experiment experiment = mockExperiment();
        return new CountTrial(user, experiment.getExperimentID());

    }

    private Experiment mockExperiment() {
        User user = mockExperimentOwner();
        Experiment experiment = new Experiment("EXPQQ7FKJB9CVDUE", "Pass or Fail",
                user.getUserId(), user.getUserName(), "keeps count if you failed or passed",
                "Count trial", "Edmonton", 10, true);
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
        CountTrial trial = mockCountTrial();
        assertEquals(0, trial.getCount());

        trial.addCount();
        assertEquals(1, trial.getCount());

        trial.addCount();
        assertEquals(2, trial.getCount());
    }

    @Test
    public void decrementCountTest() {
        CountTrial trial = mockCountTrial();
        assertEquals(0, trial.getCount());

        trial.addCount();
        assertEquals(1, trial.getCount());

        trial.addCount();
        assertEquals(2, trial.getCount());

        trial.decrementCount();
        assertEquals(1, trial.getCount());

        trial.decrementCount();
        assertEquals(0, trial.getCount());

        trial.decrementCount();
        assertEquals(-1, trial.getCount());

        trial.decrementCount();
        assertEquals(-2, trial.getCount());
    }


}

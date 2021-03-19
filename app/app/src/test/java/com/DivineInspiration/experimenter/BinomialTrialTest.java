package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.Model.UserContactInfo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinomialTrialTest {

    private BinomialTrial mockBinomialTrial() {
        User user = mockTrialOwner();
        Experiment experiment = mockExperiment();
        return new BinomialTrial(user, experiment.getExperimentID());

    }

    private Experiment mockExperiment() {
        User user = mockExperimentOwner();
        Experiment experiment = new Experiment("EXPQQ7FKJB9CVDUE", "Pass or Fail",
                user.getUserId(), user.getUserName(), "keeps count if you failed or passed",
                "Binomial trial", "Edmonton", 10, true);
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
    public void getSuccessesTest(){
        BinomialTrial trial = mockBinomialTrial();
        assertEquals(0, trial.getSuccess());
    }

    @Test
    public void getFailuresTest() {
        BinomialTrial trial = mockBinomialTrial();
        assertEquals(0, trial.getFailure());
    }
    @Test
    public void addSuccessTest() {
        BinomialTrial trial = mockBinomialTrial();
        assertEquals(0, trial.getSuccess());

        trial.addSuccess();
        assertEquals(1, trial.getSuccess());

        trial.addSuccess();
        assertEquals(2, trial.getSuccess());

    }

    @Test
    public void addFailureTest() {
        BinomialTrial trial = mockBinomialTrial();
        assertEquals(0, trial.getFailure());

        trial.addFailure();
        assertEquals(1, trial.getFailure());

        trial.addFailure();
        assertEquals(2, trial.getFailure());
    }

    @Test
    public void addTotalCountTest() {
        BinomialTrial trial = mockBinomialTrial();
        assertEquals(0, trial.getTotalCount());

        trial.addFailure();
        assertEquals(1, trial.getTotalCount());

        trial.addSuccess();
        assertEquals(2, trial.getTotalCount());

        for (int i = 0; i < 100; i++) {
            trial.addSuccess();
            trial.addFailure();
        }
        assertEquals(202, trial.getTotalCount());

    }

    @Test
    public void successRatioTest() {
        BinomialTrial trial = mockBinomialTrial();
        assertEquals(0, trial.getSuccessRatio());

        trial.addSuccess();
        assertEquals(100, trial.getSuccessRatio());
        trial.addFailure();
        assertEquals(50, trial.getSuccessRatio());
        trial.addFailure();
        assertEquals(33, trial.getSuccessRatio());
        trial.addFailure();
        assertEquals(25, trial.getSuccessRatio());
        trial.addSuccess();
        assertEquals(40, trial.getSuccessRatio());

        BinomialTrial trial2 = mockBinomialTrial();
        trial2.addFailure();
        trial2.addFailure();
        trial2.addFailure();
        assertEquals(0, trial2.getSuccessRatio());
    }
}

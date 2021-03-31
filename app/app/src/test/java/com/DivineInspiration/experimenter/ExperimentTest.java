package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.Model.UserContactInfo;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ExperimentTest{

    private Experiment mockExperiment() {
        User user = mockUser();
        Experiment experiment = new Experiment("EXPQQ7FKJB9CVDUE", "Pass or Fail",
                user.getUserId(), user.getUserName(), "keeps count if you failed or passed",
                "Binomial trial", "Edmonton", 10, true, Experiment.ONGOING);
        return experiment;
    }

    private User mockUser() {
        User user = new User("Sheldon", "XDC23DEF9K", mockContactInfo(), "aoooga");
        return user;
    }

    private UserContactInfo mockContactInfo() {
        return new UserContactInfo("Edmonton", "abcd@gmail.com");
    }

    @Test
    public void getExperimentIdTest(){
           Experiment experiment = mockExperiment();
        assertEquals("EXPQQ7FKJB9CVDUE", experiment.getExperimentID());
    }

    @Test
    public void getExperimentNameTest(){
        Experiment experiment = mockExperiment();
        assertEquals("Pass or Fail", experiment.getExperimentName());
    }

    @Test
    public void setExperimentNameTest(){
        Experiment experiment = mockExperiment();
        assertEquals("Pass or Fail", experiment.getExperimentName());

        experiment.setExperimentName("fail or pass");
        assertEquals("fail or pass", experiment.getExperimentName());
    }

    @Test
    public void getOwnerIdTest(){
        Experiment experiment = mockExperiment();
        assertEquals("XDC23DEF9K", experiment.getOwnerID());
    }


    @Test
    public void getOwnerNameTest(){
        Experiment experiment = mockExperiment();
        assertEquals("Sheldon", experiment.getOwnerName());
    }

    @Test
    public void getTrialTypeTest(){
        Experiment experiment = mockExperiment();
        assertEquals("Binomial trial", experiment.getTrialType());
    }

    @Test
    public void getRegionTest(){
        Experiment experiment = mockExperiment();
        assertEquals("Edmonton", experiment.getRegion());
    }

    @Test
    public void setRegionTest(){
        Experiment experiment = mockExperiment();
        assertEquals("Edmonton", experiment.getRegion());

        experiment.setRegion("Calgary");
        assertEquals("Calgary", experiment.getRegion());
    }

    @Test
    public void getMinimumTrialsTest(){
        Experiment experiment = mockExperiment();
        assertEquals(10, experiment.getMinimumTrials());
    }

    @Test
    public void setMinimumTrialsTest(){
        Experiment experiment = mockExperiment();
        assertEquals(10, experiment.getMinimumTrials());

        experiment.setMinimumTrials(20);
        assertEquals(20, experiment.getMinimumTrials());
    }

    @Test
    public void getExperimentDescriptionTest(){
        Experiment experiment = mockExperiment();
        assertEquals("keeps count if you failed or passed", experiment.getExperimentDescription());
    }

    @Test
    public void setExperimentDescriptionTest(){
        Experiment experiment = mockExperiment();
        assertEquals("keeps count if you failed or passed", experiment.getExperimentDescription());

        experiment.setExperimentDescription("aaaaaaaaa");
        assertEquals("aaaaaaaaa", experiment.getExperimentDescription());
    }

    @Test
    public void isRequireGeoTest(){
        Experiment experiment = mockExperiment();
        assertEquals(true, experiment.isRequireGeo());
    }

    @Test
    public void setRequireGeoTest(){
        Experiment experiment = mockExperiment();
        assertEquals(true, experiment.isRequireGeo());

        experiment.setRequireGeo(false);
        assertEquals(false, experiment.isRequireGeo());

        experiment.setRequireGeo(true);
        assertEquals(true, experiment.isRequireGeo());
    }


}
package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.MeasurementTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.Model.UserContactInfo;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeasurementTrialTest {

    private MeasurementTrial mockMeasurementTrial() {
        User user = mockTrialOwner();
        Experiment experiment = mockExperiment();

        return new MeasurementTrial(
                "test",
                user.getUserId(),
                user.getUserName(),
                experiment.getExperimentID(),
                LocalDate.now().plusDays(new Random().nextInt(40) - 20),
                (new Random()).nextDouble(),
                new LatLng(0, 0)
        );    }


    private Experiment mockExperiment() {
        User user = mockExperimentOwner();
        Experiment experiment = new Experiment("EXPQQ7FKJB9CVDUE", "Pass or Fail",
                user.getUserId(), user.getUserName(), "keeps count if you failed or passed",
                "Measurement trial", "Edmonton", 10, true, Experiment.ONGOING);
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
//
//    @Test
//    public void addMeasurementTest(){
//        MeasurementTrial trial = mockMeasurementTrial();
//        ArrayList<Float> measurements = new ArrayList<Float>();
//        assertEquals(measurements, trial.getMeasurements());
//
//        trial.addMeasurement((float)6.3);
//        measurements.add((float)6.3);
//        assertEquals(measurements, trial.getMeasurements());
//
//        trial.addMeasurement((float)0.0);
//        measurements.add((float)0.0);
//        assertEquals(measurements, trial.getMeasurements());
//
//        trial.addMeasurement((float)-5.0);
//        measurements.add((float)-5.0);
//        assertEquals(measurements, trial.getMeasurements());
//    }
//
//    @Test
//    public void averageMeasureumentTest() {
//        MeasurementTrial trial = mockMeasurementTrial();
//        boolean isNan = Double.isNaN(trial.getAverageMeasurement());
//        assertTrue(isNan);
//
//        trial.addMeasurement((float)6.3);
//        assertEquals(6.3, trial.getAverageMeasurement(), 0.01);
//
//
//        trial.addMeasurement((float)0.0);
//        assertEquals(3.15, trial.getAverageMeasurement(), 0.01);
//
//        trial.addMeasurement((float)-5.0);
//        assertEquals(0.433333, trial.getAverageMeasurement(), 0.01);
//
//        trial.addMeasurement((float)1000000.123);
//        assertEquals(250000.35575, trial.getAverageMeasurement(), 0.01);
//
//        trial.addMeasurement((float)-2000000.123);
//        assertEquals(-199999.74, trial.getAverageMeasurement(), 0.01);
//
//    }
//

}

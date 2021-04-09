package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.Trial.BinomialTrial;
import com.DivineInspiration.experimenter.Model.Trial.CountTrial;
import com.DivineInspiration.experimenter.Model.Trial.NonNegativeTrial;
import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.Model.UserContactInfo;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class CountTrialTest {

    private CountTrial mockCountTrial() {
        User user = mockTrialOwner();
        Experiment experiment = mockExperiment();

        return new CountTrial(
                "test",
                user.getUserId(),
                user.getUserName(),
                experiment.getExperimentID(),
                LocalDate.now().plusDays(new Random().nextInt(40) - 20),
                (new Random()).nextInt(20),
                new LatLng(0, 0)
        );
    }

    private Experiment mockExperiment() {
        User user = mockExperimentOwner();
        Experiment experiment = new Experiment("EXPQQ7FKJB9CVDUE",
                "Pass or Fail",
                user.getUserId(),
                user.getUserName(),
                "mock count trial",
                Trial.COUNT,
                "Edmonton",
                10,
                true,
                Experiment.ONGOING);
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
}

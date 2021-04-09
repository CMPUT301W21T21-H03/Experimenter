package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

// TODO: Figure out how to connect to firebase locally!!!

public class ExperimentManagerTest implements UserManager.OnUserReadyListener, ExperimentManager.OnOperationDone, ExperimentManager.OnExperimentListReadyListener {
    ExperimentManager exp_mgr = ExperimentManager.getInstance();
    UserManager user_mgr = UserManager.getInstance();
    User user;
    List<Experiment> experiments;

    public void onUserReady(User user) {
        this.user = user;
    }

    public void done(boolean successful) {}

    public void onExperimentsReady(List<Experiment> experiments) {
        this.experiments = experiments;
    }

    @Test
    public void testUpdateOwnerName() {
        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);

        exp_mgr.updateOwnerName("XDC2BNUY5G", "Adit Testing", this);
        user_mgr.queryUserById("XDC2BNUY5G", this);
        assertEquals("Adit Testing", user.getUserName());

        exp_mgr.updateOwnerName("XDC2BNUY5G", "Adit", this);
    }

    @Test
    public void testSubscriptions() {
        // Test subscribing to experiments
        exp_mgr.subToExperiment("XDC2BNUY5G", "EXPQR32M6L0BHLTB", this);
        exp_mgr.queryUserSubs("XDC2BNUY5G", this);
        ArrayList<String> user_ids = new ArrayList<>();
        for (Experiment e : experiments) {
            user_ids.add(e.getExperimentID());
        }
        assertTrue(user_ids.contains("XDC2BNUY5G"));


        // Test unsubscribing to experiments
        exp_mgr.unSubFromExperiment("XDC2BNUY5G", "EXPQR32M6L0BHLTB", this);
        exp_mgr.queryUserSubs("XDC2BNUY5G", this);
        user_ids = new ArrayList<>();
        for (Experiment e : experiments) {
            user_ids.add(e.getExperimentID());
        }
        assertFalse(user_ids.contains("XDC2BNUY5G"));
    }

    @Test
    public void testAddExperiment() {
        // Test adding an experiment
        Experiment mockExp = new Experiment("EXPQR32ERT0BHLTB", "Test Exp", "XDC2BNUY5G", "No des", "Binomial", "Edmonton", 5, false, "On going");
        exp_mgr.addExperiment(mockExp, this);
        exp_mgr.queryAll(this);
        ArrayList<String> experiment_ids = new ArrayList<>();
        for (Experiment e : experiments) {
            experiment_ids.add(e.getExperimentID());
        }
        assertTrue(experiment_ids.contains("EXPQR32ERT0BHLTB"));

        // Test deleting an experiment
        exp_mgr.deleteExperiment("EXPQR32ERT0BHLTB", this);
        exp_mgr.queryAll(this);
        experiment_ids = new ArrayList<>();
        for (Experiment e : experiments) {
            experiment_ids.add(e.getExperimentID());
        }
        assertFalse(experiment_ids.contains("EXPQR32ERT0BHLTB"));
    }
}
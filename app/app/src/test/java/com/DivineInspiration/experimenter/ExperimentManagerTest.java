package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Controller.ExperimentManager;
import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.Experiment;
import com.DivineInspiration.experimenter.Model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ExperimentManagerTest implements UserManager.OnUserReadyListener, ExperimentManager.OnOperationDone {
    ExperimentManager exp_mgr = ExperimentManager.getInstance();
    UserManager user_mgr = UserManager.getInstance();
    User user;

    public void onUserReady(User user) {
        this.user = user;
    }

    public void done(boolean successful) {}


    public void testUpdateOwnerName() {
        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);
        
        exp_mgr.updateOwnerName("XDC2BNUY5G", "Adit Testing", this);
        user_mgr.queryUserById("XDC2BNUY5G", this);
        assertEquals("Adit Testing", user.getUserName());

        exp_mgr.updateOwnerName("XDC2BNUY5G", "Adit", this);


    }
}

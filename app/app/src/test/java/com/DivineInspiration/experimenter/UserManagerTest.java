package com.DivineInspiration.experimenter;

import com.DivineInspiration.experimenter.Controller.UserManager;
import com.DivineInspiration.experimenter.Model.User;
import com.DivineInspiration.experimenter.Model.UserContactInfo;

import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

// TODO: Figure out how to connect to firebase locally!!!
// TODO: Figure out how to automate testing of sharedPreferences

public class UserManagerTest implements UserManager.OnUserReadyListener, UserManager.OnUserListReadyListener {
    UserManager user_mgr = UserManager.getInstance();
    User user;
    ArrayList<User> users;

    public void onUserReady(User user) {
        this.user = user;
    }

    public void onUserListReady(ArrayList<User> users) {
        this.users = users;
    }

    public void testUpdateUser() {
        // Test updating the user
        User mockUser = new User("Adit Test", "XDC2CNZT5G", new UserContactInfo("Ed", "email"), "No des");
        user_mgr.updateUser(mockUser, this);

        // Test queryUserById
        user_mgr.queryUserById("XDC2CNZT5G", this);
        assertEquals("Adit Test", user.getUserName());

        // Test queryUserByName
        user_mgr.queryUserByName("Adit Test", this);
        assertEquals("XDC2CNZT5G", user.getUserId());
    }
}

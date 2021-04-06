package com.DivineInspiration.experimenter.Activity;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.DivineInspiration.experimenter.R;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserProfileTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() {
        Activity activity = rule.getActivity();
    }


    //Changes the users profile settings twice in case of multiple runes of the intent test
    //asserts changes are completed
    @Test
    public void editProfile() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.clickOnMenuItem("Edit");

        solo.clearEditText((EditText) solo.getView(R.id.editProfileName));
        solo.enterText((EditText) solo.getView(R.id.editProfileName), "Test Name");
        solo.clearEditText((EditText) solo.getView(R.id.editTrialType));
        solo.enterText((EditText) solo.getView(R.id.editTrialType), "testEmail@gmail.com");
        solo.clearEditText((EditText) solo.getView(R.id.editProfileCity));
        solo.enterText((EditText) solo.getView(R.id.editProfileCity), "Edmonton");
        solo.clearEditText((EditText) solo.getView(R.id.editExperimentAbout));
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "This is for intent testing");
        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Test Name", 1, 2000));
        assertTrue(solo.waitForText("testEmail@gmail.com", 1, 2000));
        assertTrue(solo.waitForText("Edmonton", 1, 2000));
        assertTrue(solo.waitForText("This is for intent testing", 1, 2000));
        solo.clickOnMenuItem("Edit");

        solo.clearEditText((EditText) solo.getView(R.id.editProfileName));
        solo.enterText((EditText) solo.getView(R.id.editProfileName), "Test Name2");
        solo.clearEditText((EditText) solo.getView(R.id.editTrialType));
        solo.enterText((EditText) solo.getView(R.id.editTrialType), "testEmail2@gmail.com");
        solo.clearEditText((EditText) solo.getView(R.id.editProfileCity));
        solo.enterText((EditText) solo.getView(R.id.editProfileCity), "Calgary");
        solo.clearEditText((EditText) solo.getView(R.id.editExperimentAbout));
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "This is for intent testing2");
        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Test Name2", 1, 2000));
        assertTrue(solo.waitForText("testEmail2@gmail.com", 1, 2000));
        assertTrue(solo.waitForText("Calgary", 1, 2000));
        assertTrue(solo.waitForText("This is for intent testing2", 1, 2000));
    }

    //Checks if its possible to change your username to a username already taken
    @Test
    public void editProfileUserNameInUse() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.clickOnMenuItem("Edit");

        solo.clearEditText((EditText) solo.getView(R.id.editProfileName));
        solo.enterText((EditText) solo.getView(R.id.editProfileName), "UserName");
        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Name taken", 1, 2000));
    }
}

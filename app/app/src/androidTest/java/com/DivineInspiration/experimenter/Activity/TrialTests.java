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

public class TrialTests {
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

    @Test
    public void checkAddCountTrial() {
        //creates the count experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test counting Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test counting experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Test counting Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region", 1, 2000));
        assertTrue(solo.waitForText("this is a test counting experiment for intent testing", 1, 2000));
        assertTrue(solo.waitForText("Geolocation: On", 1, 2000));

        //subscribes to the experiment so we can create trials
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create trial on experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editTextNumber), "3");
        solo.clickOnCheckBox(0);
        solo.clickOnText("Submit");

        //TODO add check if you can add a non geo location on one that requires it

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkAddBinomialTrial() {
        //creates the Binomial experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test Binomial Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test binomial experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,1);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Test Binomial Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region", 1, 2000));
        assertTrue(solo.waitForText("this is a test binomial experiment for intent testing", 1, 2000));
        assertTrue(solo.waitForText("Geolocation: On", 1, 2000));

        //subscribes to the experiment so we can create trials
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create trial on experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editTextNumber), "3");
        solo.clickOnCheckBox(0);

        solo.clickOnText("Pass");
        assertTrue(solo.waitForText("Passed: 1", 1, 2000));
        assertTrue(solo.waitForText("Failed: 0", 1, 2000));

        solo.clickOnText("Pass");
        assertTrue(solo.waitForText("Passed: 2", 1, 2000));
        assertTrue(solo.waitForText("Failed: 0", 1, 2000));

        solo.clickOnText("Fail");
        assertTrue(solo.waitForText("Passed: 2", 1, 2000));
        assertTrue(solo.waitForText("Failed: 1", 1, 2000));

        solo.clickOnText("Pass");
        assertTrue(solo.waitForText("Passed: 3", 1, 2000));
        assertTrue(solo.waitForText("Failed: 1", 1, 2000));

        solo.clickOnText("Fail");
        assertTrue(solo.waitForText("Passed: 3", 1, 2000));
        assertTrue(solo.waitForText("Failed: 2", 1, 2000));
        solo.clickOnText("Submit");

        //TODO add check if you can add a non geo location on one that requires it

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkAddNonNegTrial() {
        //creates the count experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test NonNeg Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test NonNeg experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,2);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Test NonNeg Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region", 1, 2000));
        assertTrue(solo.waitForText("this is a test NonNeg experiment for intent testing", 1, 2000));
        assertTrue(solo.waitForText("Geolocation: On", 1, 2000));

        //subscribes to the experiment so we can create trials
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create trial on experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editTextNumber), "123");
        solo.clickOnCheckBox(0);
        solo.clickOnText("Submit");

        //TODO add check if you can add a non geo location on one that requires it

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkAddMeasuringTrial() {
        //creates the count experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test measuring Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test measuring experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,3);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Test measuring Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region", 1, 2000));
        assertTrue(solo.waitForText("this is a test measuring experiment for intent testing", 1, 2000));
        assertTrue(solo.waitForText("Geolocation: On", 1, 2000));

        //subscribes to the experiment so we can create trials
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create trial on experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editTextNumber), "123");
        solo.clickOnCheckBox(0);
        solo.clickOnText("Submit");

        //TODO add check if you can add a non geo location on one that requires it

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }
}

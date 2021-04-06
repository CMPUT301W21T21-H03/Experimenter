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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateExperimentTest {

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


    //Creates one of each type of experiment asserts they exist and then deletes them
    //asserts they no longer exist
    //issue with test if multiples of exact same experiment
    @Test
    public void checkExperimentCreationAndDeletion() {
        solo.clickOnView(solo.getView(R.id.fab));

        //creates counting experiment
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

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

        assertFalse(solo.waitForText("Test counting Experiment", 1, 2000));
        assertFalse(solo.waitForText("Test region", 1, 2000));
        assertFalse(solo.waitForText("this is a test counting experiment for intent testing", 1, 2000));


        //creates Binomial experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test Binomial Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region1");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test Binomial experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,1);

        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Test Binomial Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region1", 1, 2000));
        assertTrue(solo.waitForText("this is a test Binomial experiment for intent testing", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

        assertFalse(solo.waitForText("Test Binomial Experiment", 1, 2000));
        assertFalse(solo.waitForText("Test region1", 1, 2000));
        assertFalse(solo.waitForText("this is a test Binomial experiment for intent testing", 1, 2000));

        //Creates Non-neg experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test NonNegative Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region2");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test NonNegative experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,2);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Test NonNegative Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region2", 1, 2000));
        assertTrue(solo.waitForText("this is a test NonNegative experiment for intent testing", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

        assertFalse(solo.waitForText("Test NonNegative Experiment", 1, 2000));
        assertFalse(solo.waitForText("Test region2", 1, 2000));
        assertFalse(solo.waitForText("this is a test NonNegative experiment for intent testing", 1, 2000));

        //creates Measuring experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test measuring Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region3");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test measuring experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,2);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Test measuring Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region3", 1, 2000));
        assertTrue(solo.waitForText("this is a test measuring experiment for intent testing", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

        assertFalse(solo.waitForText("Test measuring Experiment", 1, 2000));
        assertFalse(solo.waitForText("Test region3", 1, 2000));
        assertFalse(solo.waitForText("this is a test measuring experiment for intent testing", 1, 2000));
    }

    @Test
    public void checkExperimentUnpublish() {
        solo.clickOnView(solo.getView(R.id.fab));

        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing unpublish");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Testing region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to unpublish for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Testing unpublish", 1, 2000));
        assertTrue(solo.waitForText("Testing region", 1, 2000));
        assertTrue(solo.waitForText("this is a test experiment to unpublish for intent testing", 1, 2000));


        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("End");

        assertTrue(solo.waitForText("Status: Ended", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        assertTrue(solo.waitForText("This experiment has ended!", 1, 2000));
    }
    @Test
    public void checkEditExperiment() {
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing edit");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Testing region0");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to edit for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Testing unpublish", 1, 2000));
        assertTrue(solo.waitForText("Testing region0", 1, 2000));
        assertTrue(solo.waitForText("this is a test experiment to unpublish for intent testing", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.setting));

        solo.clearEditText((EditText) solo.getView(R.id.editExperimentName));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing edit1");
        solo.clearEditText((EditText) solo.getView(R.id.editExperimentCity));
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Testing region1");
        solo.clearEditText((EditText) solo.getView(R.id.editExperimentAbout));
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to edit for intent testing1");
        solo.clearEditText((EditText) solo.getView(R.id.editExperimentMin));
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "1001");
        solo.pressSpinnerItem(0,1);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Home");
        assertTrue(solo.waitForText("Testing edit1", 1, 2000));
        assertTrue(solo.waitForText("Testing region1", 1, 2000));
        assertTrue(solo.waitForText("this is a test experiment to edit for intent testing1", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

    }
    @Test
    public void checkSubscribe() {
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing subscribe");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));
        solo.clickOnMenuItem("Home");

        solo.clickOnMenuItem("Subscriptions");
        assertTrue(solo.waitForText("Testing subscribe", 1, 2000));
        assertTrue(solo.waitForText("Test region", 1, 2000));
        assertTrue(solo.waitForText("this is a test experiment to subscribe to for intent testing", 1, 2000));

        solo.clickOnMenuItem("Home");
        solo.sleep(5000);

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }



}

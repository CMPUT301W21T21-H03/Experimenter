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

public class ExperimentTests {

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


    //Creates a count experiment asserts they exist and then deletes them
    //asserts they no longer exist
    //issue with test if multiples of exact same experiment
    @Test
    public void checkCountExperimentCreationAndDeletion() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));

        //creates counting experiment
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test counting Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test counting experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0, 0);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Test counting Experiment", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        assertTrue(solo.waitForText("Test counting Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region", 1, 2000));
        assertTrue(solo.waitForText("this is a test counting experiment for intent testing", 1, 2000));
        assertTrue(solo.waitForText("Geolocation: On", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));
        while (solo.waitForText("Delete", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }

        solo.clickOnMenuItem("Delete");

        assertFalse(solo.waitForText("Test counting Experiment", 1, 2000));
        assertFalse(solo.waitForText("Test region", 1, 2000));
        assertFalse(solo.waitForText("this is a test counting experiment for intent testing", 1, 2000));
    }

    //Creates a Binomial experiment asserts they exist and then deletes them
    //asserts they no longer exist
    //issue with test if multiples of exact same experiment
    @Test
    public void checkBinomialExperimentCreationAndDeletion() {
        //creates Binomial experiment
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test Binomial Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region1");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test Binomial experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0, 1);

        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Test Binomial Experiment", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        assertTrue(solo.waitForText("Test Binomial Experiment", 1, 10000));
        assertTrue(solo.waitForText("Test region1", 1, 2000));
        assertTrue(solo.waitForText("this is a test Binomial experiment for intent testing", 1, 2000));


        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));

        while (solo.waitForText("Delete", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }

        solo.clickOnMenuItem("Delete");

        assertFalse(solo.waitForText("Test Binomial Experiment", 1, 2000));
        assertFalse(solo.waitForText("Test region1", 1, 2000));
        assertFalse(solo.waitForText("this is a test Binomial experiment for intent testing", 1, 2000));
    }

    //Creates a nonNeg experiment asserts they exist and then deletes them
    //asserts they no longer exist
    //issue with test if multiples of exact same experiment
    @Test
    public void checkNonNegExperimentCreationAndDeletion() {
        //Creates Non-neg experiment
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test NonNegative Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region2");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test NonNegative experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0, 2);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Test NonNegative Experiment", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        assertTrue(solo.waitForText("Test NonNegative Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region2", 1, 2000));
        assertTrue(solo.waitForText("this is a test NonNegative experiment for intent testing", 1, 2000));


        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));
        while (solo.waitForText("Delete", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Delete");

        assertFalse(solo.waitForText("Test NonNegative Experiment", 1, 2000));
        assertFalse(solo.waitForText("Test region2", 1, 2000));
        assertFalse(solo.waitForText("this is a test NonNegative experiment for intent testing", 1, 2000));
    }

    //Creates a Measuring experiment asserts they exist and then deletes them
    //asserts they no longer exist
    //issue with test if multiples of exact same experiment
    @Test
    public void checkMeasuringExperimentCreationAndDeletion(){
        //creates Measuring experiment
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test measuring Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region3");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test measuring experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,2);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Test measuring Experiment", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        assertTrue(solo.waitForText("Test measuring Experiment", 1, 2000));
        assertTrue(solo.waitForText("Test region3", 1, 2000));
        assertTrue(solo.waitForText("this is a test measuring experiment for intent testing", 1, 2000));


        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));

        while (solo.waitForText("Delete", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Delete");

        assertFalse(solo.waitForText("Test measuring Experiment", 1, 2000));
        assertFalse(solo.waitForText("Test region3", 1, 2000));
        assertFalse(solo.waitForText("this is a test measuring experiment for intent testing", 1, 2000));
    }
    //attempts to create experiments that are past the character limit
    //checks to see that the title/region/description are cut off at the current place
    @Test
    public void checkExperimentCreationOverCharLimit() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing the character Limit test " +
                "Testing the character Limit test Testing the character Limit test Testing the character Limit test " +
                "Testing the character Limit test Testing the character Limit test Testing the character Limit test");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Testing region Testing region" +
                "Testing region Testing region Testing region Testing region Testing region Testing region" +
                "Testing region Testing region Testing region Testing region Testing region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment " +
                "to test charcater limits for intent testing this is a test experiment to test charcater " +
                "limits for intent testing this is a test experiment to test charcater limits for intent " +
                "testing this is a test experiment to test charcater limits for intent testing this is a " +
                "test experiment to test charcater limits for intent testing this is a test experiment to " +
                "test charcater limits for intent testing this is a test experiment to test charcater " +
                "limits for intent testing this is a test experiment to test charcater limits for intent" +
                "testing this is a test experiment to test charcater limits for intent testing this is a " +
                "test experiment to test charcater limits for intent testing this is a test experiment to " +
                "test charcater limits for intent testing this is a test experiment to test charcater limits " +
                "for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100000");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing the character Limit test " +
                "Testing the character Limit test Testing the character Limit test T", 1, 2000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        solo.drag(600, 600, 1000, 1500, 10);
        solo.drag(600, 600, 1000, 1500, 10);
        //checks to make sure the longer then 100/100/500 character experiment wasnt created
        assertFalse(solo.waitForText("Testing the character Limit test " +
                "Testing the character Limit test Testing the character Limit test Testing the character Limit test " +
                "Testing the character Limit test Testing the character Limit test Testing the character Limit test", 1, 2000));
        assertFalse(solo.waitForText("Testing region Testing region" +
                "Testing region Testing region Testing region Testing region Testing region Testing region" +
                "Testing region Testing region Testing region Testing region Testing region", 1, 2000));
        assertFalse(solo.waitForText("this is a test experiment " +
                "to test charcater limits for intent testing this is a test experiment to test charcater " +
                "limits for intent testing this is a test experiment to test charcater limits for intent " +
                "testing this is a test experiment to test charcater limits for intent testing this is a " +
                "test experiment to test charcater limits for intent testing this is a test experiment to " +
                "test charcater limits for intent testing this is a test experiment to test charcater " +
                "limits for intent testing this is a test experiment to test charcater limits for intent" +
                "testing this is a test experiment to test charcater limits for intent testing this is a " +
                "test experiment to test charcater limits for intent testing this is a test experiment to " +
                "test charcater limits for intent testing this is a test experiment to test charcater limits " +
                "for intent testing", 1, 2000));

        //Checks if the title/region/character are the first 100/100/500 characters of what was typed
        assertTrue(solo.waitForText("Testing the character Limit test " +
                        "Testing the character Limit test Testing the character Limit test T", 1, 2000));
        assertTrue(solo.waitForText("Testing region Testing region" +
                "Testing region Testing region Testing region Testing region Testing reg", 1, 2000));
        assertTrue(solo.waitForText("this is a test experiment " +
                "to test charcater limits for intent testing this is a test experiment to test charcater " +
                "limits for intent testing this is a test experiment to test charcater limits for intent " +
                "testing this is a test experiment to test charcater limits for intent testing this is a " +
                "test experiment to test charcater limits for intent testing this is a test experiment to " +
                "test charcater limits for intent testing this is a test experiment to test charcater " +
                "limits for intent testing this is a", 1, 2000));

        //deletes the experiment after we are done

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));

        while (solo.waitForText("Delete", 1, 1000) == false) {
            solo.drag(600, 600, 900, 600, 10);
        }
        solo.drag(600, 600, 900, 600, 10);
        solo.drag(600, 600, 800, 600, 10);
        assertTrue(solo.waitForText("Delete", 1, 2000));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkExperimentUnpublish() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));

        int randomInt = (int)Math.floor(Math.random()*(100000000-0+1)+0);
        String testName = "Testing unpublish"+ String.valueOf(randomInt);

        solo.enterText((EditText) solo.getView(R.id.editExperimentName), testName);
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Testing region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to unpublish for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);

        solo.clickOnMenuItem("Ok");

        while (solo.waitForText(testName, 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        assertTrue(solo.waitForText(testName, 1, 2000));
        assertTrue(solo.waitForText("Testing region", 1, 2000));
        assertTrue(solo.waitForText("this is a test experiment to unpublish for intent testing", 1, 2000));


        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("End");

        assertTrue(solo.waitForText("Status: Ended", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        assertTrue(solo.waitForText("Delete", 1, 2000));
        solo.clickOnMenuItem("Delete");
    }
    @Test
    public void checkEditExperiment() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing edit");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Testing region0");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to edit for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing edit", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        assertTrue(solo.waitForText("Testing edit", 1, 2000));
        assertTrue(solo.waitForText("Testing region0", 1, 2000));
        assertTrue(solo.waitForText("this is a test experiment to edit for intent testing", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
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

        solo.clickOnView(solo.getView(R.id.navigation_home));

        while (solo.waitForText("Testing edit", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        assertTrue(solo.waitForText("Testing edit1", 1, 2000));
        assertTrue(solo.waitForText("Testing region1", 1, 2000));
        assertTrue(solo.waitForText("this is a test experiment to edit for intent testing1", 1, 2000));


        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.setting));

        while (solo.waitForText("Delete", 1, 1000) == false) {
            solo.drag(600, 600, 900, 600, 10);
        }
        solo.drag(600, 600, 900, 600, 10);

        solo.clickOnMenuItem("Delete");


    }
    @Test
    public void checkSubscribe() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing subscribe");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing subscribe", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));
        solo.clickOnView(solo.getView(R.id.navigation_home));

        solo.clickOnMenuItem("Subscriptions");
        assertTrue(solo.waitForText("Testing subscribe", 1, 2000));
        assertTrue(solo.waitForText("Test region", 1, 2000));
        assertTrue(solo.waitForText("this is a test experiment to subscribe to for intent testing", 1, 2000));

        solo.clickOnView(solo.getView(R.id.navigation_home));

        while (solo.waitForText("Testing subscribe", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));

        while (solo.waitForText("Delete", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Delete");
    }
    @Test
    public void checkMap() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing Binomial trial stats");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,1);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing Binomial trial stats", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //adding pass trials
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 3; i++) {
            solo.clickOnView(solo.getView(R.id.binomial_pass_button));
        }
        for (int i = 0; i < 2; i++) {
            solo.clickOnView(solo.getView(R.id.binomial_fail_button));
        }
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Map");

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

    }

    @Test
    public void checkMapWithNoGeoTrials() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing Binomial trial stats");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,1);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing Binomial trial stats", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //adding pass trials
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 3; i++) {
            solo.clickOnView(solo.getView(R.id.binomial_pass_button));
        }
        for (int i = 0; i < 2; i++) {
            solo.clickOnView(solo.getView(R.id.binomial_fail_button));
        }
        solo.clickOnMenuItem("Ok");

        assertFalse(solo.waitForText("Map", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

    }
}

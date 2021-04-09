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

public class StatisticsTest {
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
    public void checkCountTrialStats() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing count trial statistics");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0, 0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing count trial statistics", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create trial on experiment
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 10; i++) {
            solo.clickOnText("+");
        }
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 15; i++) {
            solo.clickOnText("+");
        }
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 5; i++) {
            solo.clickOnText("+");
        }
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");
        assertTrue(solo.waitForText("Total Trial Count:", 1, 2000));
        assertTrue(solo.waitForText("5", 1, 2000));
        assertTrue(solo.waitForText("Total count:", 1, 2000));
        assertTrue(solo.waitForText("32", 1, 2000));
        assertTrue(solo.waitForText("Median:", 1, 2000));
        assertTrue(solo.waitForText("5.0000", 1, 2000));
        assertTrue(solo.waitForText("Mean:", 1, 2000));
        assertTrue(solo.waitForText("6.4000", 1, 2000));
        assertTrue(solo.waitForText("Standard Deviation:", 1, 2000));
        assertTrue(solo.waitForText("5.4259", 1, 2000));
        assertTrue(solo.waitForText("Q1:", 1, 2000));
        assertTrue(solo.waitForText("1", 3, 2000));
        assertTrue(solo.waitForText("Q3:", 1, 2000));
        assertTrue(solo.waitForText("12.5", 1, 2000));
        assertTrue(solo.waitForText("Min:", 1, 2000));
        assertTrue(solo.waitForText("1", 3, 2000));
        assertTrue(solo.waitForText("Max:", 1, 2000));
        assertTrue(solo.waitForText("15", 1, 2000));

        solo.clickOnMenuItem("Trials");
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");
        assertTrue(solo.waitForText("Total Trial Count:", 1, 2000));
        assertTrue(solo.waitForText("6", 1, 2000));
        assertTrue(solo.waitForText("Total count:", 1, 2000));
        assertTrue(solo.waitForText("33", 1, 2000));
        assertTrue(solo.waitForText("Median:", 1, 2000));
        assertTrue(solo.waitForText("3.0000", 1, 2000));
        assertTrue(solo.waitForText("Mean:", 1, 2000));
        assertTrue(solo.waitForText("5.5000", 1, 2000));
        assertTrue(solo.waitForText("Standard Deviation:", 1, 2000));
        assertTrue(solo.waitForText("5.3463", 1, 2000));
        assertTrue(solo.waitForText("Q1:", 1, 2000));
        assertTrue(solo.waitForText("1", 3, 2000));
        assertTrue(solo.waitForText("Q3:", 1, 2000));
        assertTrue(solo.waitForText("10", 1, 2000));
        assertTrue(solo.waitForText("Min:", 1, 2000));
        assertTrue(solo.waitForText("1", 3, 2000));
        assertTrue(solo.waitForText("Max:", 1, 2000));
        assertTrue(solo.waitForText("15", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }
    @Test
    public void checkBinomialTrialStats() {
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
        for (int i = 0; i < 15; i++) {
            solo.clickOnView(solo.getView(R.id.binomial_pass_button));
        }
        for (int i = 0; i < 10; i++) {
            solo.clickOnView(solo.getView(R.id.binomial_fail_button));
        }
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");
        assertTrue(solo.waitForText("Total Trial Count:", 1, 2000));
        assertTrue(solo.waitForText("25", 1, 2000));
        assertTrue(solo.waitForText("Passes:", 1, 2000));
        assertTrue(solo.waitForText("15", 1, 2000));
        assertTrue(solo.waitForText("Fails:", 1, 2000));
        assertTrue(solo.waitForText("10", 1, 2000));
        assertTrue(solo.waitForText("Ratio", 1, 2000));
        assertTrue(solo.waitForText("60.00", 1, 2000));
        assertTrue(solo.waitForText("Mean:", 1, 2000));
        assertTrue(solo.waitForText("0.6000", 1, 2000));
        assertTrue(solo.waitForText("Standard Deviation", 1, 2000));
        assertTrue(solo.waitForText("0.4899", 1, 2000));

        solo.clickOnMenuItem("Trials");
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 5; i++) {
            solo.clickOnView(solo.getView(R.id.binomial_pass_button));
        };
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");
        assertTrue(solo.waitForText("Total Trial Count:", 1, 2000));
        assertTrue(solo.waitForText("30", 1, 2000));
        assertTrue(solo.waitForText("Passes:", 1, 2000));
        assertTrue(solo.waitForText("20", 1, 2000));
        assertTrue(solo.waitForText("Fails:", 1, 2000));
        assertTrue(solo.waitForText("10", 1, 2000));
        assertTrue(solo.waitForText("Ratio", 1, 2000));
        assertTrue(solo.waitForText("66.67", 1, 2000));
        assertTrue(solo.waitForText("Mean:", 1, 2000));
        assertTrue(solo.waitForText("0.6667", 1, 2000));
        assertTrue(solo.waitForText("Standard Deviation", 1, 2000));
        assertTrue(solo.waitForText("0.4714", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkNonNegTrialStats() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing nonNeg trial stats");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,2);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing nonNeg trial stats", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //checks adding trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 7; i++) {
            solo.clickOnView(solo.getView(R.id.increase_trial_value));
        }
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 3; i++) {
            solo.clickOnView(solo.getView(R.id.increase_trial_value));
        }
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 10; i++) {
            solo.clickOnView(solo.getView(R.id.increase_trial_value));
        }
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 5; i++) {
            solo.clickOnView(solo.getView(R.id.increase_trial_value));
        }
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");
        assertTrue(solo.waitForText("Total Trial Count:", 1, 2000));
        assertTrue(solo.waitForText("7", 2, 2000));
        assertTrue(solo.waitForText("Median:", 1, 2000));
        assertTrue(solo.waitForText("3.0000", 1, 2000));
        assertTrue(solo.waitForText("Mean:", 1, 2000));
        assertTrue(solo.waitForText("4.2857", 1, 2000));
        assertTrue(solo.waitForText("Standard Deviation:", 1, 2000));
        assertTrue(solo.waitForText("3.0102", 1, 2000));
        assertTrue(solo.waitForText("Q1:", 1, 2000));
        assertTrue(solo.waitForText("2", 3, 2000));
        assertTrue(solo.waitForText("Q3:", 1, 2000));
        assertTrue(solo.waitForText("7", 1, 2000));
        assertTrue(solo.waitForText("Min:", 1, 2000));
        assertTrue(solo.waitForText("1", 3, 2000));
        assertTrue(solo.waitForText("Max:", 1, 2000));
        assertTrue(solo.waitForText("10", 1, 2000));

        solo.clickOnMenuItem("Trials");
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 5; i++) {
            solo.clickOnView(solo.getView(R.id.increase_trial_value));
        };
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");
        assertTrue(solo.waitForText("Total Trial Count:", 1, 2000));
        assertTrue(solo.waitForText("8", 2, 2000));
        assertTrue(solo.waitForText("Median:", 1, 2000));
        assertTrue(solo.waitForText("4.0000", 1, 2000));
        assertTrue(solo.waitForText("Mean:", 1, 2000));
        assertTrue(solo.waitForText("4.3750", 1, 2000));
        assertTrue(solo.waitForText("Standard Deviation:", 1, 2000));
        assertTrue(solo.waitForText("2.8257", 1, 2000));
        assertTrue(solo.waitForText("Q1:", 1, 2000));
        assertTrue(solo.waitForText("2", 2, 2000));
        assertTrue(solo.waitForText("Q3:", 1, 2000));
        assertTrue(solo.waitForText("6", 1, 2000));
        assertTrue(solo.waitForText("Min:", 1, 2000));
        assertTrue(solo.waitForText("1", 3, 2000));
        assertTrue(solo.waitForText("Max:", 1, 2000));
        assertTrue(solo.waitForText("10", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkMeasuringTrialStats() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing measuring trial stats");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,3);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing measuring trial stats", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "12.3");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "-2.3");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "75");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "22");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "4.6");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "1.2");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "22.34");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "72.4");
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");
        assertTrue(solo.waitForText("Total Trial Count:", 1, 2000));
        assertTrue(solo.waitForText("8", 2, 2000));
        assertTrue(solo.waitForText("Median:", 1, 2000));
        assertTrue(solo.waitForText("17.1500", 1, 2000));
        assertTrue(solo.waitForText("Mean:", 1, 2000));
        assertTrue(solo.waitForText("25.9425", 1, 2000));
        assertTrue(solo.waitForText("Standard Deviation:", 1, 2000));
        assertTrue(solo.waitForText("28.8204", 1, 2000));
        assertTrue(solo.waitForText("Q1:", 1, 2000));
        assertTrue(solo.waitForText("2.9", 1, 2000));
        assertTrue(solo.waitForText("Q3:", 1, 2000));
        //assertTrue(solo.waitForText("47.37", 1, 2000));
        assertTrue(solo.waitForText("Min:", 1, 2000));
        assertTrue(solo.waitForText("-2.3", 1, 2000));
        assertTrue(solo.waitForText("Max:", 1, 2000));
        assertTrue(solo.waitForText("75", 1, 2000));

        solo.clickOnMenuItem("Trials");
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "100");
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");
        assertTrue(solo.waitForText("Total Trial Count:", 1, 2000));
        assertTrue(solo.waitForText("9", 2, 2000));
        assertTrue(solo.waitForText("Median:", 1, 2000));
        assertTrue(solo.waitForText("22.0000", 1, 2000));
        assertTrue(solo.waitForText("Mean:", 1, 2000));
        assertTrue(solo.waitForText("34.1711", 1, 2000));
        assertTrue(solo.waitForText("Standard Deviation:", 1, 2000));
        assertTrue(solo.waitForText("35.7771", 1, 2000));
        assertTrue(solo.waitForText("Q1:", 1, 2000));
        assertTrue(solo.waitForText("2.9", 1, 2000));
        assertTrue(solo.waitForText("Q3:", 1, 2000));
        assertTrue(solo.waitForText("73.7", 1, 2000));
        assertTrue(solo.waitForText("Min:", 1, 2000));
        assertTrue(solo.waitForText("-2.3", 1, 2000));
        assertTrue(solo.waitForText("Max:", 1, 2000));
        assertTrue(solo.waitForText("100", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkCountTrialLineGraph() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing count trial line graph");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0, 0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing count trial line graph", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create trial on experiment
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");

        while (solo.waitForText("Line graph", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Line graph");
        solo.drag(200, 200, 300, 100, 10);
        solo.drag(200, 200, 300, 100, 10);

        assertTrue(solo.waitForView(R.id.statBackButton));

        solo.clickOnView(solo.getView(R.id.statBackButton));

        solo.drag(200, 200, 100, 300, 10);
        solo.drag(200, 200, 100, 300, 10);
        solo.waitForView(solo.getView(R.id.setting), 1, 2000);
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

    }
    @Test
    public void checkBinomialTrialLineGraph() {
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

        solo.clickOnMenuItem("Stats");

        while (solo.waitForText("Line graph", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Line graph");
        solo.drag(200, 200, 300, 100, 10);
        solo.drag(200, 200, 300, 100, 10);


        assertTrue(solo.waitForView(R.id.statBackButton));

        solo.clickOnView(solo.getView(R.id.statBackButton));

        solo.drag(200, 200, 100, 300, 10);
        solo.drag(200, 200, 100, 300, 10);
        solo.waitForView(solo.getView(R.id.setting), 1, 2000);
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }
    @Test
    public void checkNonNegTrialLineGraph() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing NonNeg trial stats");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,2);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing NonNeg trial stats", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //adding pass trials
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");

        while (solo.waitForText("Line graph", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Line graph");
        solo.drag(200, 200, 300, 100, 10);
        solo.drag(200, 200, 300, 100, 10);


        assertTrue(solo.waitForView(R.id.statBackButton));

        solo.clickOnView(solo.getView(R.id.statBackButton));

        solo.drag(200, 200, 100, 300, 10);
        solo.drag(200, 200, 100, 300, 10);
        solo.waitForView(solo.getView(R.id.setting), 1, 2000);
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkMeasuringTrialLineGraph() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing measuring trial stats");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,3);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing measuring trial stats", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "12.3");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "1");
        solo.clickOnMenuItem("Ok");


        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "3.9");
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");

        while (solo.waitForText("Line graph", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Line graph");
        solo.drag(200, 200, 300, 100, 10);
        solo.drag(200, 200, 300, 100, 10);

        assertTrue(solo.waitForView(R.id.statBackButton));

        solo.clickOnView(solo.getView(R.id.statBackButton));

        solo.drag(200, 200, 100, 300, 10);
        solo.drag(200, 200, 100, 300, 10);
        solo.waitForView(solo.getView(R.id.setting), 1, 2000);
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkCountTrialHistogram() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing count trial line graph");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0, 0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing count trial line graph", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create trial on experiment
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");

        while (solo.waitForText("Histogram", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Histogram");
        solo.drag(200, 200, 300, 100, 10);
        solo.drag(200, 200, 300, 100, 10);

        assertTrue(solo.waitForView(R.id.statBackButton));

        solo.clickOnView(solo.getView(R.id.statBackButton));

        solo.drag(200, 200, 100, 300, 10);
        solo.drag(200, 200, 100, 300, 10);

        solo.waitForView(solo.getView(R.id.setting), 1, 2000);
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkBinomialTrialHistogram() {
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

        solo.clickOnMenuItem("Stats");

        while (solo.waitForText("Histogram", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Histogram");
        solo.drag(200, 200, 300, 100, 10);
        solo.drag(200, 200, 300, 100, 10);

        assertTrue(solo.waitForView(R.id.statBackButton));

        solo.clickOnView(solo.getView(R.id.statBackButton));

        solo.drag(200, 200, 100, 300, 10);
        solo.drag(200, 200, 100, 300, 10);
        solo.waitForView(solo.getView(R.id.setting), 1, 2000);
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkNonNegCTrialHistogram() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing NonNeg trial histogram");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,2);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing NonNeg trial histogram", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");

        while (solo.waitForText("Histogram", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Histogram");
        solo.drag(200, 200, 300, 100, 10);
        solo.drag(200, 200, 300, 100, 10);

        assertTrue(solo.waitForView(R.id.statBackButton));

        solo.clickOnView(solo.getView(R.id.statBackButton));

        solo.drag(200, 200, 100, 300, 10);
        solo.drag(200, 200, 100, 300, 10);
        solo.waitForView(solo.getView(R.id.setting), 1, 2000);
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkMeasuringTrialHistogram() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing measuring trial stats");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,3);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing measuring trial stats", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "12.3");
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "1");
        solo.clickOnMenuItem("Ok");


        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "3.9");
        solo.clickOnMenuItem("Ok");

        solo.clickOnMenuItem("Stats");

        while (solo.waitForText("Histogram", 1, 1000) == false) {
            solo.drag(200, 200, 300, 100, 10);
        }
        solo.clickOnMenuItem("Histogram");
        solo.drag(200, 200, 300, 100, 10);
        solo.drag(200, 200, 300, 100, 10);

        assertTrue(solo.waitForView(R.id.statBackButton));

        solo.clickOnView(solo.getView(R.id.statBackButton));

        solo.drag(200, 200, 100, 300, 10);
        solo.drag(200, 200, 100, 300, 10);
        solo.waitForView(solo.getView(R.id.setting), 1, 2000);
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }
}

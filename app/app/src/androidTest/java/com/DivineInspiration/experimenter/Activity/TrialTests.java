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
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing count trial");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing count trial", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create trial on experiment
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Result: 1", 1, 2000));

        //Checks to make sure we cant put a negative count trial in
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnButton("-");
        solo.clickOnButton("-");
        solo.clickOnButton("-");
        solo.clickOnMenuItem("Ok");

        assertFalse(solo.waitForText("Result: -3", 1, 2000));

        //Checks if we can put a trial through that doesnt have geolocation on a geolocation required experiment
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");

        assertFalse(solo.waitForText("Result: 1", 2, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkAddBinomialTrial() {
        //creates the Binomial experiment
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing Binomial trial");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,1);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing Binomial trial", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 4000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //Checks to make sure we cant have a negative amount of false trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.binomial_fail_decrement));
        solo.clickOnView(solo.getView(R.id.binomial_fail_decrement));
        solo.clickOnMenuItem("Ok");
        assertFalse(solo.waitForText("Result: False", 1, 2000));

        //Checks to make sure we cant have a negative amount of pass trials
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.binomial_pass_decrement));
        solo.clickOnView(solo.getView(R.id.binomial_pass_decrement));
        solo.clickOnMenuItem("Ok");
        assertFalse(solo.waitForText("Result: Pass", 1, 2000));

        //adding pass trials
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.binomial_pass_button));
        solo.clickOnView(solo.getView(R.id.binomial_pass_button));
        solo.clickOnMenuItem("Ok");
        assertTrue(solo.waitForText("Result: Pass", 2, 2000));

        //adding fail trials
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.binomial_fail_button));
        solo.clickOnView(solo.getView(R.id.binomial_fail_button));
        solo.clickOnMenuItem("Ok");
        assertTrue(solo.waitForText("Result: False", 2, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkAddNonNegTrial() {
        //creates the NonNeg experiment
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing nonNeg trial");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,2);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing nonNeg trial", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 4000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //checks adding trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnView(solo.getView(R.id.increase_trial_value));
        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Result: 2", 1, 2000));

        //checks if you can add a negative trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnView(solo.getView(R.id.decrease_trial_value));
        solo.clickOnView(solo.getView(R.id.decrease_trial_value));
        solo.clickOnMenuItem("Ok");
        assertFalse(solo.waitForText("Result: -2", 1, 2000));
        assertTrue(solo.waitForText("Result: 0", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkAddMeasuringTrial() {
        //creates the count experiment
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing measuring trial");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,3);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing measuring trial", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create positive measurement trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "12.3");
        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Result: 12.3", 1, 2000));

        //creates large positive measurement trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "10000000");
        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Result: 1.0E7", 1, 2000));

        //creates negative measurement trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "-72.3");
        solo.clickOnMenuItem("Ok");

        //creates large negative measurement trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "-50000000");
        solo.clickOnMenuItem("Ok");
        assertTrue(solo.waitForText("Result: -5.0E7", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkNonGeoTrial() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing count trial");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing count trial", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //checks to make sure no location is added if not checked
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnMenuItem("Ok");
        assertFalse(solo.waitForText("Location:", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");
        assertTrue(solo.waitForText("Location:", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

    }
    @Test
    public void checkMakeQR() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Testing QR code generation");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,3);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Testing QR code generation", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create positive measurement trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "12.3");
        solo.clickOnView(solo.getView(R.id.qr_code_generator));

        solo.waitForView(R.id.qrFileName);
        solo.enterText((EditText) solo.getView(R.id.qrFileName), "Testing QR generator");

        solo.clickOnMenuItem("Save");
        solo.waitForText("Ok");
        solo.clickOnMenuItem("Ok");

        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkMakeBarCode() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "testing barcode");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment to subscribe to for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,3);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("testing barcode", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //create positive measurement trial
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.editMeasurementValue), "12.3");
        solo.clickOnView(solo.getView(R.id.barCodeButton));

        assertTrue(solo.waitForView(R.id.scanButton));

        solo.clickLongOnScreen(0,0);

        solo.waitForText("Ok");
        solo.clickOnMenuItem("Cancel");

        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");

    }
}

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
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.clickOnText("+");
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 10; i++) {
            solo.clickOnText("+");
        }

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 15; i++) {
            solo.clickOnText("+");
        }
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        for (int i = 0; i < 5; i++) {
            solo.clickOnText("+");
        }
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");
    }
}

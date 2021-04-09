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

public class ExploreTest {

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
    public void checkSearch() {
        solo.assertCurrentActivity("Wrong activity",MainActivity .class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));

        //creates the experiment that we will search for
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test searching Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test search region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout),
                "this is a test experiment created for testing the search function using intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        //Searches for the experiment based on its name
        solo.clickOnView(solo.getView(R.id.navigation_explore));
        assertTrue(solo.waitForText("Search", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.explore_search_bar), "Test searching Experiment");
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        assertTrue(solo.waitForText("Test searching Experiment", 1, 2000));

        //Searches for the experiment based on a part of its name
        solo.clickOnView(solo.getView(R.id.navigation_explore));
        assertTrue(solo.waitForText("Search", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.explore_search_bar), "Test searching");
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        assertTrue(solo.waitForText("Test searching Experiment", 1, 2000));

        //Searches for the experiment based on its description
        solo.clickOnView(solo.getView(R.id.navigation_explore));
        assertTrue(solo.waitForText("Search", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.explore_search_bar),
                "this is a test experiment created for testing the search function using intent testing");
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        assertTrue(solo.waitForText("Test searching Experiment", 1, 2000));

        //Searches for the experiment based on a portion of its description
        solo.clickOnView(solo.getView(R.id.navigation_explore));
        assertTrue(solo.waitForText("Search", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.explore_search_bar),
                "search function using intent testing");
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        assertTrue(solo.waitForText("Test searching Experiment", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }
}

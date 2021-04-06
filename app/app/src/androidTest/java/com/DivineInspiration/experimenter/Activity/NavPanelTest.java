package com.DivineInspiration.experimenter.Activity;


import android.app.Activity;

import com.robotium.solo.Solo;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import static org.junit.Assert.assertTrue;


public class NavPanelTest {
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
    public  void navBarChange() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.clickOnMenuItem("Explore");
        assertTrue(solo.waitForText("Search", 1, 2000));
        solo.clickOnMenuItem("Scan");

        assertTrue(solo.waitForText("Place a barcode inside the viewfinder rectangle to scan it.",
                2, 2000));

    }

}

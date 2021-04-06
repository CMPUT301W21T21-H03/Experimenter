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

public class DiscussionForumTest {

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
    public void checkAddComment() {
        //creates test experiment
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test discussion forum Experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test discussion forum experiment for intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,3);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        //subscribes so we are allowed to comment
        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //creates the comment
        solo.clickOnMenuItem("Comments");
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment");
        solo.clickOnMenuItem("Ok");

        //checks if comment was made
        assertTrue(solo.waitForText("this is a test comment", 1, 2000));

        //Repeat of creation/assertion 3 more times
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment2");
        solo.clickOnMenuItem("Ok");
        assertTrue(solo.waitForText("this is a test comment2", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment3");
        solo.clickOnMenuItem("Ok");
        assertTrue(solo.waitForText("this is a test comment3", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment4");
        solo.clickOnMenuItem("Ok");
        assertTrue(solo.waitForText("this is a test comment4", 1, 2000));

        //Deletes the experiment we made
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }
}

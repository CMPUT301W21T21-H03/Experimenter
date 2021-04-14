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
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test discussion forum experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment for discussion forum intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Test discussion forum experiment", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //creates the comment
        solo.clickOnMenuItem("Comments");
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment");
        solo.clickOnMenuItem("Ok");

        //checks if comment was made
        while (solo.waitForText("this is a test comment", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        assertTrue(solo.waitForText("this is a test comment", 1, 2000));

        //Repeat of creation/assertion 5 more times
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment2");
        solo.clickOnMenuItem("Ok");
        while (solo.waitForText("this is a test comment2", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        assertTrue(solo.waitForText("this is a test comment2", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment3");
        solo.clickOnMenuItem("Ok");
        while (solo.waitForText("this is a test comment3", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        assertTrue(solo.waitForText("this is a test comment3", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment4");
        solo.clickOnMenuItem("Ok");
        while (solo.waitForText("this is a test comment4", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        assertTrue(solo.waitForText("this is a test comment4", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment5");
        solo.clickOnMenuItem("Ok");
        while (solo.waitForText("this is a test comment5", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        assertTrue(solo.waitForText("this is a test comment4", 1, 2000));

        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment6");
        solo.clickOnMenuItem("Ok");
        while (solo.waitForText("this is a test comment6", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        assertTrue(solo.waitForText("this is a test comment4", 1, 2000));



        //Deletes the experiment we made
        while (solo.waitForText("Created by", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }
        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }

    @Test
    public void checkAddReply() {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForView(solo.getView(R.id.fab), 1, 2000);
        solo.clickOnView(solo.getView(R.id.fab));
        solo.enterText((EditText) solo.getView(R.id.editExperimentName), "Test discussion forum experiment");
        solo.enterText((EditText) solo.getView(R.id.editExperimentCity), "Test region");
        solo.enterText((EditText) solo.getView(R.id.editExperimentAbout), "this is a test experiment for discussion forum intent testing");
        solo.enterText((EditText) solo.getView(R.id.editExperimentMin), "100");
        solo.pressSpinnerItem(0,0);
        solo.clickOnCheckBox(0);
        solo.clickOnMenuItem("Ok");

        while (solo.waitForText("Test discussion forum experiment", 1, 1000) == false) {
            solo.drag(600, 600, 1000, 1500, 10);
        }

        solo.clickOnView(solo.getView(R.id.experimentItemCard));
        assertTrue(solo.waitForText("Status:", 1, 2000));
        solo.clickOnView(solo.getView(R.id.subscribeSwitch));

        //creates the comment
        solo.clickOnMenuItem("Comments");
        solo.clickOnView(solo.getView(R.id.experiment_fragment_add_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test comment");
        solo.clickOnMenuItem("Ok");

        //creates the reply
        solo.clickOnView(solo.getView(R.id.add_reply_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test reply");
        solo.clickOnMenuItem("Ok");
        solo.waitForView(R.id.view_replies_button, 1, 2000);
        solo.clickOnView(solo.getView(R.id.view_replies_button));
        assertTrue(solo.waitForText("this is a test reply", 1, 2000));

        //second reply
        solo.clickOnView(solo.getView(R.id.add_reply_button));
        solo.enterText((EditText) solo.getView(R.id.create_comment_edit_text), "this is a test reply2");
        solo.clickOnMenuItem("Ok");
        solo.waitForView(R.id.view_replies_button, 1, 2000);
        solo.clickOnView(solo.getView(R.id.view_replies_button));
        assertTrue(solo.waitForText("this is a test reply2", 1, 2000));

        solo.clickOnView(solo.getView(R.id.setting));
        solo.clickOnMenuItem("Delete");
    }
}

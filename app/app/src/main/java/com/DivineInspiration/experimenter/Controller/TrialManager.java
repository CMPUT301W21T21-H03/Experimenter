package com.DivineInspiration.experimenter.Controller;

import com.DivineInspiration.experimenter.Model.Trial.Trial;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

// Talks to firebase
public class TrialManager extends ArrayList<Trial> {

    private ArrayList<Trial> trials;
    private String trialType;
    private FirebaseFirestore db;

    // TODO Talk to firebase

    // maybe better to do in experiments class
//    private int[] blocklist;

    /**
     * Constructor
     * @param trialType
     * type of trial
     */
    public TrialManager(String trialType) {
        // inits
        this.trials = new ArrayList<>();
        this.trialType = trialType;
    }

    /**
     * Gets the type of the trials
     * @return
     * returns the type of the trial
     */
    public String getTrialType() {
        return trialType;
    }

    /**
     * Sets the trial type
     * @param trialType
     * trial type
     */
    public void setTrialType(String trialType) {
        this.trialType = trialType;
    }

    /**
     * Trial Manager contructor
     */
    public TrialManager() {
        this.trials = new ArrayList<>();
    }

    /**
     * Gets all trials
     * @return
     * an array list of all trials
     */
    public ArrayList<Trial> getTrials(){
        return trials;
    }

    /**
     * Gets trial at a particular index
     * @param index
     * index of trial
     * @return
     * returns trial at index
     */
    public Trial getTrial(int index) {
        return trials.get(index);
    }
}

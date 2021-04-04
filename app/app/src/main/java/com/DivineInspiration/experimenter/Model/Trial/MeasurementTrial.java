package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class MeasurementTrial extends Trial {
    private float value;

    //constructor with location
    public MeasurementTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, float value, double latitude, double longitude){
        super(trialID, trialUserID, trialExperimentID, trialDate, latitude, longitude);
        this.trialType = Trial.MEASURE;
        this.value = value;
    }

    //mock constructor
    public MeasurementTrial() {

        super("test", "test", "test", LocalDate.now().plusDays(new Random().nextInt(40) - 20));
        this.trialType = Trial.COUNT;
        Random rng = new Random();

        value = rng.nextFloat() * 20 - 20;
    }

    /**
     * Constructor
     *
     * @param trialUserID       user of this trial
     * @param trialExperimentID id of experiment
     */
    public MeasurementTrial(String trialUserID, String trialExperimentID) {
        super(trialUserID, trialExperimentID);
        this.trialType = Trial.MEASURE;
        this.trialExperimentID = trialExperimentID;
    }

    /**
     * Constructor
     *
     * @param trialID           the id of this trial
     * @param trialDate         the date of this trial
     * @param trialUserID       user of this trial
     * @param trialExperimentID id of experiment
     * @param measurements      list of measurments for this trial
     */
    public MeasurementTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, float measurements) {
        super(trialID, trialUserID, trialExperimentID, trialDate);
        this.trialType = Trial.MEASURE;

        this.value = measurements;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float newValue) {
        value = newValue;
    }


}
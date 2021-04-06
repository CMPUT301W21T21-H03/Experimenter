package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class MeasurementTrial extends Trial {
    private double value;

    //constructor with location
    public MeasurementTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, double value, GeoPoint location){
        super(trialID, trialUserID, trialExperimentID, trialDate, location);
        this.trialType = Trial.MEASURE;
        this.value = value;
    }

    //mock constructor
    public MeasurementTrial() {

        super("test", "test", "test", LocalDate.now().plusDays(new Random().nextInt(40) - 20));
        this.trialType = Trial.MEASURE;
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
    public MeasurementTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, double measurements) {
        super(trialID, trialUserID, trialExperimentID, trialDate);
        this.trialType = Trial.MEASURE;

        this.value = measurements;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double newValue) {
        value = newValue;
    }


}
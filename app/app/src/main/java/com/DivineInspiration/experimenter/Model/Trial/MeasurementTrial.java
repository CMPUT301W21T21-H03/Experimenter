package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MeasurementTrial extends Trial {
    public ArrayList<Float> measurements = new ArrayList<Float>();

    /**
     * Constructor
     * @param trialUserID
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     */
    public MeasurementTrial(String trialUserID, String trialExperimentID) {
        super(trialUserID, trialExperimentID);
        this.trialType = Trial.MEASURE;
        this.trialExperimentID = trialExperimentID;
    }

    /**
     * Constructor
     * @param trialID
     * the id of this trial
     * @param trialDate
     * the date of this trial
     * @param trialUserID
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     * @param measurements
     * list of measurments for this trial
     */
    public MeasurementTrial(String trialID, String trialUserID, String trialExperimentID, LocalDate trialDate, ArrayList<Float> measurements) {
        super(trialID, trialUserID, trialExperimentID, trialDate);
        this.trialType = Trial.MEASURE;

        this.measurements = measurements;
    }

    /**
     * Adds measurement to measurements
     * @param: measurement
     */
    public void addMeasurement(float measurement) {
        measurements.add(measurement);
    }

    /**
     * Gets all measurements
     * @return: measurements:ArrayList<Float>
     */
    public ArrayList<Float> getMeasurements() {
        return measurements;
    }

    /**
     * Return the average measurement
     * @return: average measurement
     */
    public float getAverageMeasurement() {
        float total = 0;
        for (float measurement : measurements) {
            total += measurement;
        }
        return total / measurements.size();
    }
}
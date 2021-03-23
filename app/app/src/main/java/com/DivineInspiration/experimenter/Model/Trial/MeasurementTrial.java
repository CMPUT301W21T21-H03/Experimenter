package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MeasurementTrial extends Trial {
    public ArrayList<Float> measurements = new ArrayList<Float>();

    /**
     * Constructor
     * @param trialUser
     * user of this trial
     * @param trialExperimentID
     * id of experiment
     */
    public MeasurementTrial(User trialUser, String trialExperimentID) {
        this.trialID = UUID.randomUUID().toString();
        this.trialDate = new Date();
        this.trialUser = trialUser;
        this.trialExperimentID = trialExperimentID;
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
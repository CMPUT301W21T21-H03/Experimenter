package com.DivineInspiration.experimenter.Model.Trial;

import android.annotation.SuppressLint;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;


public class MeasurementTrial extends Trial {
    private double value;

    /**
     * The constructor
     * @param trialID
     * ID of trial
     * @param trialUserID
     * ID of user
     * @param trialOwnerName
     * name of the experimenter that did the trial
     * @param trialExperimentID
     * ID of the experiment
     * @param trialDate
     * date of when the trial occurred
     * @param value
     * measurement value
     * @param location
     * location of where the trial occurred
     */
    public MeasurementTrial(String trialID, String trialUserID,String trialOwnerName ,String trialExperimentID, LocalDate trialDate, double value, LatLng location){
        super(trialID, trialUserID, trialOwnerName ,trialExperimentID, trialDate, location);
        this.trialType = Trial.MEASURE;
        this.value = value;
    }

    /**
     * The constructor
     * @param trialUserID
     * ID of user
     * @param trialOwnerName
     * name of the experimenter that did the trial
     * @param trialExperimentID
     * ID of the experiment
     * @param value
     * measurement value
     * @param location
     * location of where the trial occurred
     */
    public MeasurementTrial(String trialUserID,String trialOwnerName ,String trialExperimentID, double value, LatLng location) {
        super(trialUserID, trialOwnerName ,trialExperimentID);
        this.trialType = Trial.MEASURE;
        this.trialExperimentID = trialExperimentID;
        this.value = value;
        this.location = location;
    }

    /**
     * Gets current value of the measurement
     * @return value 
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets value of the measurement
     * @param newValue :double (new measurement)
     */
    public void setValue(double newValue) {
        value = newValue;
    }

    /**
     * String representation of trial
     * @return pretty print of trial
     */
    @SuppressLint("DefaultLocale")
    @NotNull
    public String toString(){
        return String.format("MeasureTrial %s: %.3f, date: %s", trialID, value, trialDate.toString());
    }
}
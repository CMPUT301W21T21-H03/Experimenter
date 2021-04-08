package com.DivineInspiration.experimenter.Model.Trial;

import android.annotation.SuppressLint;

import com.DivineInspiration.experimenter.Model.User;

import org.jetbrains.annotations.NotNull;
import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class MeasurementTrial extends Trial {
    private double value;

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, value:double, location:GeoPoint
     */
    public MeasurementTrial(String trialID, String trialUserID,String trialOwnerName ,String trialExperimentID, LocalDate trialDate, double value, GeoPoint location){
        super(trialID, trialUserID, trialOwnerName ,trialExperimentID, trialDate, location);
        this.trialType = Trial.MEASURE;
        this.value = value;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate
     */
    public MeasurementTrial(String trialUserID,String trialOwnerName ,String trialExperimentID) {
        super(trialUserID, trialOwnerName ,trialExperimentID);
        this.trialType = Trial.MEASURE;
        this.trialExperimentID = trialExperimentID;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, value:double, location:GeoPoint
     */
    public MeasurementTrial(String trialUserID,String trialOwnerName ,String trialExperimentID, double value, GeoPoint location) {
        super(trialUserID, trialOwnerName ,trialExperimentID);
        this.trialType = Trial.MEASURE;
        this.trialExperimentID = trialExperimentID;
        this.value = value;
        this.location = location;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, value:double
     */
    public MeasurementTrial(String trialID, String trialUserID,String trialOwnerName ,String trialExperimentID, LocalDate trialDate, double value) {
        super(trialID, trialUserID, trialOwnerName ,trialExperimentID, trialDate);
        this.trialType = Trial.MEASURE;
        this.value = value;
    }

    /**
     * Mock object constructor for testing purposes
     * @param: void
     */
    public MeasurementTrial() {
        super("test", "test", "test", "test",LocalDate.now().plusDays(new Random().nextInt(40) - 20));
        this.trialType = Trial.MEASURE;
        Random rng = new Random();
        value = rng.nextFloat() * 20;
    }

    /**
     * Gets current value of the measurement
     * @return: value:int
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets value of the measurement
     * @param: value:double (new measurement)
     */
    public void setValue(double newValue) {
        value = newValue;
    }

    @SuppressLint("DefaultLocale")
    @NotNull
    public String toString(){
        return String.format("MeasureTrial %s: %.3f, date: %s", trialID, value, trialDate.toString());
    }
}
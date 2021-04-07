package com.DivineInspiration.experimenter.Model.Trial;

import com.DivineInspiration.experimenter.Model.User;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class NonNegativeTrial extends Trial {
    private int count;

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, count:int, location:GeoPoint
     */
    public NonNegativeTrial(String trialID, String trialUserID,String trialOwnerName ,String trialExperimentID, LocalDate trialDate, int count, GeoPoint location){
        super(trialID, trialUserID, trialOwnerName ,trialExperimentID, trialDate,location);
        this.trialType = Trial.NONNEGATIVE;
        this.count = count;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate
     */
    public NonNegativeTrial(String trialUserID, String trialOwnerName, String trialExperimentID) {
        super(trialUserID, trialOwnerName,trialExperimentID);
        this.trialType = Trial.NONNEGATIVE;
        this.count = 0;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, count:int, location:GeoPoint
     */
    public NonNegativeTrial(String trialUserID,String trialOwnerName ,String trialExperimentID, int count, GeoPoint location) {
        super(trialUserID, trialOwnerName,trialExperimentID);
        this.trialType = Trial.NONNEGATIVE;
        this.count = count;
        this.location = location;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, count:int
     */
    public NonNegativeTrial(String trialID, String trialUserID,String trialOwnerName ,String trialExperimentID, LocalDate trialDate, int count) {
        super(trialID, trialUserID,trialOwnerName ,trialExperimentID, trialDate);
        this.trialType = Trial.NONNEGATIVE;
        this.count = count;
    }

    /**
     * Mock object constructor for testing purposes
     * @param: void
     */
    public NonNegativeTrial(){
        super("test", "test", "test","test", LocalDate.now().plusDays(new Random().nextInt(40) - 20));
        this.trialType = Trial.NONNEGATIVE;
        Random rng = new Random();
        count = rng.nextInt(20);
    }

    /**
     * Increments count by one
     */
    public void addCount() {
        ++count;
    }

    /**
     * Sets count
     * @param: count:int (new count)
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets current count
     * @return: count:int
     */
    public int getCount() {
        return count;
    }
}
package com.DivineInspiration.experimenter.Model.Trial;

import android.content.Context;

import com.DivineInspiration.experimenter.Model.User;

import org.osmdroid.util.GeoPoint;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class CountTrial extends Trial {
    private int count;

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, count:int, location:GeoPoint
     */
    public CountTrial(String trialID, String trialUserID,String trialOwnerName ,String trialExperimentID, LocalDate trialDate, int count, GeoPoint location){
        super(trialID, trialUserID,trialOwnerName ,trialExperimentID, trialDate, location);
        this.trialType = Trial.COUNT;
        this.count = count;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate
     */
    public CountTrial(String trialUserID,String trialOwnerName ,String trialExperimentID) {
        super(trialUserID,trialOwnerName ,trialExperimentID);
        this.trialType = Trial.COUNT;
        this.count = 0;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, count:int, location:GeoPoint
     */
    public CountTrial(String trialUserID,String trialOwnerName ,String trialExperimentID, int count, GeoPoint location) {
        super(trialUserID,trialOwnerName ,trialExperimentID);
        this.trialType = Trial.COUNT;
        this.count = count;
        this.location = location;
    }

    /**
     * Constructor
     * @param: trialId:String, userId:String, trialOwnerName:String, trialExperimentID:String, date:LocalDate, count:int
     */
    public CountTrial(String trialID, String trialUserID,String trialOwnerName ,String trialExperimentID, LocalDate trialDate, int count) {
        super(trialID, trialUserID, trialOwnerName ,trialExperimentID, trialDate);
        this.trialType = Trial.COUNT;
        this.count = count;
    }

    /**
     * Mock object constructor for testing purposes
     * @param: void
     */
    public CountTrial(){
        super("test", "test", "test","test" ,LocalDate.now().plusDays(new Random().nextInt(20)));
        this.trialType = Trial.COUNT;
        this.count =new Random().nextInt(20);
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
     * Decrements count by one
     */
    public void decrementCount() {
        --count;
    }

    /**
     * Gets current count
     * @return: count:int
     */
    public int getCount() {
        return count;
    }
}

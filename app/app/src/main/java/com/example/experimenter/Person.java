package com.example.experimenter;

import java.util.UUID;

public class Person {
    private String uniqueID;
    private String namePerson;
    private boolean typeOwner;
    private ContactPersonInfo contactPersonInfo;


    //constructor

    public Person(String namePerson, boolean typeOwner, ContactPersonInfo contactPersonInfo) {
        this.uniqueID = UUID.randomUUID().toString();
        this.namePerson = namePerson;
        this.typeOwner = typeOwner;
        this.contactPersonInfo = contactPersonInfo;
    }

    //getters and setter
    public ContactPersonInfo getContactPersonInfo() {
        return contactPersonInfo;
    }

    public void setContactPersonInfo(ContactPersonInfo contactPersonInfo) {
        this.contactPersonInfo = contactPersonInfo;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public boolean isTypeOwner() {
        return typeOwner;
    }

    public void setTypeOwner(boolean typeOwner) {
        this.typeOwner = typeOwner;
    }

    public String getUniqueID() {
        return uniqueID;
    }
    //Create Event
    public void createExperiment(String experimentName, String experimentDescription){
        String experimentOwnerID = getUniqueID();
        new Experiment(experimentName,experimentOwnerID,experimentDescription);
    }

    // Subscribe to Event
    public void subscribeExperiment(Experiment experiment){
        String subscriberID = getUniqueID();
        experiment.addSubscribers(subscriberID);
    }
    //Unsubscribe to Event
    public void unsubscribeExperiment(Experiment experiment){
        String subscriberID = getUniqueID();
        experiment.deleteSubscribers(subscriberID);
    }
}

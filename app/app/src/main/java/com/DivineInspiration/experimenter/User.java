package com.DivineInspiration.experimenter;

import java.util.UUID;

public class User {
    private String uniqueID;
    private String username;
    private ContactPersonInfo contactUserInfo;

    /**
     * User contructor
     * @param username
     * name of the person
     * @param contactUserInfo
     * contact info on that person
     */
    public User(String username, ContactPersonInfo contactUserInfo) {
        this.uniqueID = UUID.randomUUID().toString();
        this.username = username;

        this.contactUserInfo = contactUserInfo;
    }

    /**
     * Gets contact info of user
     * @return
     * Contact person class of user
     */
    public ContactPersonInfo getContactUserInfo() {
        return contactUserInfo;
    }

    /**
     * Sets new contact info of person
     * @param contactUserInfo
     * new contact info
     */
    public void setContactUserInfo(ContactPersonInfo contactUserInfo) {
        this.contactUserInfo = contactUserInfo;
    }

    /**
     * Gets name of user
     * @return
     * Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username
     * @param username
     * User name
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets ID of user
     * @return
     * User ID
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * Creates a new experiment under user => move to owner?
     * @param experimentName
     * @param experimentDescription
     */
    public void createExperiment(String experimentName, String experimentDescription){
        String experimentOwnerID = getUniqueID();
        new Experiment(experimentName,experimentOwnerID,experimentDescription);
    }

    /**
     * Subscribes to an experiment
     * @param experiment
     * experiment to subscribe to
     */
    public void subscribeExperiment(Experiment experiment){
        String subscriberID = getUniqueID();
        experiment.addSubscriber(subscriberID);
    }

    /**
     * Unsubscribes an experiment
     * @param experiment
     * experiment to unsubscribe to
     */
    public void unsubscribeExperiment(Experiment experiment){
        String subscriberID = getUniqueID();
        experiment.deleteSubscriber(subscriberID);
    }
}

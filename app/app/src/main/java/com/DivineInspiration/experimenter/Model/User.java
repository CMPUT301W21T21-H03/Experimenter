package com.DivineInspiration.experimenter.Model;

import org.jetbrains.annotations.NotNull;

/**
 * A class holding user profile information.
 */
public class User {
    private String userId;
    private String userName;
    private String description;
    private UserContactInfo contactInfo;

    /**
     * User constructor
     * @param username name of the user
     * @param contactUserInfo the contact info of the user
     * @param description the user description
     */
    public User(String username, String userId, UserContactInfo contactUserInfo, String description) {
        this.userId = userId;
        this.userName = username;
        this.contactInfo = contactUserInfo;
        this.description = description;
    }

    /**
     * Default constructor when initializing the user
     * @param userId ID of user
     */
    public User(String userId) {
        this.userId = userId;
        userName = "Anonymous";
        contactInfo = new UserContactInfo();
        description = "";
    }

    /**
     * Mock object constructor for testing purposes
     */
    public User() {
        userId = "defaultId";
        userName = "UserName";
        contactInfo = new UserContactInfo();
        description = "";
    }

    /**
     * Gets contact info of user
     * @return contact info of user
     */
    public UserContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets new contact info of person
     * @param contactInfo new contact info
     */
    public void setContactInfo(UserContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Gets name of user
     * @return name of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets a new username for the user
     * @param userName the new name of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the ID of user
     * @return ID of user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns a formatted user string
     * @return formatted string
     */
    @NotNull
    @Override
    public String toString(){
        return String.format("User Name: %s, Id: %s", userName, userId);
    }

    /**
     * Gets the user description
     * @return the user description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a new user description
     * @param newDescription the new user description
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
}